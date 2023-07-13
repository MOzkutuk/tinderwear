package com.alpha.tinderware.controller;

import com.alpha.tinderware.dao.*;
import com.alpha.tinderware.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final UserRatingRepository userRatingRepository;
    private final UserDAO userDAO;
    private final UserBodyRepository userBodyRepository;
    private final MatchParametersRepository matchParametersRepository;
    private final ProductDAO productDAO;
    private final TopWearRepository topWearRepository;
    private final BottomWearRepository bottomWearRepository;

    @Autowired
    public RecommendationController(UserRatingRepository userRatingRepository,
                                    UserDAO userDAO,
                                    UserBodyRepository userBodyRepository,
                                    MatchParametersRepository matchParametersRepository,
                                    ProductDAO productDAO,
                                    TopWearRepository topWearRepository,
                                    BottomWearRepository bottomWearRepository) {
        this.userRatingRepository = userRatingRepository;
        this.userDAO = userDAO;
        this.userBodyRepository = userBodyRepository;
        this.matchParametersRepository = matchParametersRepository;
        this.productDAO = productDAO;
        this.topWearRepository = topWearRepository;
        this.bottomWearRepository = bottomWearRepository;
    }

    @PostMapping("/addRatings")
    public String addRatings(@RequestParam int idu, @RequestParam int idp, @RequestParam int rating) {
        UserRating userRating = new UserRating();
        userRating.setIdu(idu);
        userRating.setIdp(idp);
        userRating.setRating(rating);

        userRatingRepository.save(userRating);

        return "Rating " + rating + " from user " + idu + " for the product ID " + idp + " has been submitted";
    }

    @GetMapping("/matchBody")
    public ResponseEntity<List<Product>> getProductMatchBody(@RequestParam int idu) {

        Optional<User> userOpt = userDAO.findById(idu);
        UserBody userBody = userBodyRepository.findByIdu(idu).orElse(null);

        if (userOpt.isEmpty() || userBody == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userOpt.get();

        String colorPalette = userBody.getColorPalette();
        String bodyShape = userBody.getBodyShape();
        String height = userBody.getHeight();
        String gender = user.getGender();

        List<Integer> productIds = matchParametersRepository.findMatchByParameters(colorPalette, bodyShape, height)
                .stream()
                .map(MatchParameters::getIdp)
                .collect(Collectors.toList());

        List<Product> products = productDAO.findAllByIdInAndGender(productIds, gender);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/topWearPreferences")
    public Map<String, Map<String, Double>> getPreferencesTopWear(@RequestParam int idu) {

        List<UserRating> userRatedProducts = userRatingRepository.findByIdu(idu);

        List<Integer> ratedProductIds = userRatedProducts
                .stream()
                .map(UserRating::getIdp)
                .collect(Collectors.toList());

        List<TopWear> topWearProducts = topWearRepository.findAllById(ratedProductIds);

        Map<String, Map<String, Double>> topWearPreferenceWeights = new HashMap<>();

        String[] attributes = { "Style", "Fit", "Material", "Fabric", "Pattern", "Color" };

        for (String attribute : attributes) {
            topWearPreferenceWeights.put(attribute, new HashMap<String, Double>() {{ put("default", 0.0); }});
        }

        for (UserRating ratedProduct : userRatedProducts) {

            TopWear matchedProduct = topWearProducts.stream()
                    .filter(topWear -> topWear.getId() == (ratedProduct.getIdp()))
                    .findFirst()
                    .orElse(null);

            double rating = (double) ratedProduct.getRating();
            double weight = rating / userRatedProducts.stream().mapToInt(UserRating::getRating).sum();

            if (matchedProduct != null) {
                addToPreferenceWeights(topWearPreferenceWeights, "Style", matchedProduct.getStyle(), weight);
                addToPreferenceWeights(topWearPreferenceWeights, "Fit", matchedProduct.getFit(), weight);
                addToPreferenceWeights(topWearPreferenceWeights, "Material", matchedProduct.getMaterial(), weight);
                addToPreferenceWeights(topWearPreferenceWeights, "Fabric", matchedProduct.getFabric(), weight);
                addToPreferenceWeights(topWearPreferenceWeights, "Pattern", matchedProduct.getPattern(), weight);
                addToPreferenceWeights(topWearPreferenceWeights, "Color", matchedProduct.getColor(), weight);
            }
        }

        return topWearPreferenceWeights;
    }

    private void addToPreferenceWeights(Map<String, Map<String, Double>> preferenceWeights, String attribute, String value, double weight) {
        Map<String, Double> attributeWeights = preferenceWeights.get(attribute);

        if (attributeWeights.containsKey(value)) {
            attributeWeights.put(value, attributeWeights.get(value) + weight);
        } else {
            attributeWeights.put(value, weight);
        }
    }

    //for bottom wear

//    @GetMapping("/bottomWear")
//    public ResponseEntity<Map<String, Object>> getBottomWearProducts(@RequestParam("Idu") int idu) {
//        Map<String, Map<String, Double>> productsPreferenceWeights = getPreferencesBottomWear(idu);
//
//        User user = userRepository.findById(idu);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        List<Integer> userRatedProductIds = userRatingRepository.findById(idu)
//                .stream()
//                .map(UserRating::getIdp)
//                .collect(Collectors.toList());
//
//        List<Integer> productIds = productDAO.findAllByGender(user.getGender())
//                .stream()
//                .map(Product::getId)
//                .collect(Collectors.toList());
//
//        List<BottomWear> matchingProducts = bottomWearRepository.findAllById(productIds);
//
//        List<BottomWear> orderedProducts = matchingProducts.stream()
//                .filter(p -> preferenceContainsValue(productsPreferenceWeights, "Style", p.getStyle()) ||
//                        preferenceContainsValue(productsPreferenceWeights, "Fit", p.getFit()) ||
//                        preferenceContainsValue(productsPreferenceWeights, "Material", p.getMaterial()) ||
//                        preferenceContainsValue(productsPreferenceWeights, "Fabric", p.getFabric()) ||
//                        preferenceContainsValue(productsPreferenceWeights, "Pattern", p.getPattern()) ||
//                        preferenceContainsValue(productsPreferenceWeights, "Color", p.getColor()))
//                .sorted((p1, p2) -> Double.compare(calculateProductWeightBottomWear(p2, productsPreferenceWeights),
//                        calculateProductWeightBottomWear(p1, productsPreferenceWeights)))
//                .collect(Collectors.toList());
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("userRatedProductIds", userRatedProductIds);
//        result.put("products", productIds);
//        result.put("matchingProducts", matchingProducts);
//
//        return ResponseEntity.ok(result);
//    }



//    @GetMapping("/bottomWearPreferences")
//    public Map<String, Map<String, Double>> getPreferencesBottomWear(@RequestParam int idu) {
//
//        List<UserRating> userRatedProducts = userRatingRepository.findByIdu(idu);
//
//        List<Integer> ratedProductIds = userRatedProducts
//                .stream()
//                .map(UserRating::getIdp)
//                .collect(Collectors.toList());
//
//        List<BottomWear> bottomWearProducts = bottomWearRepository.findAllById(ratedProductIds);
//
//        Map<String, Map<String, Double>> bottomWearPreferenceWeights = createDefaultPreferenceWeights();
//
//        for (UserRating ratedProduct : userRatedProducts) {
//
//            BottomWear matchedProduct = bottomWearProducts.stream()
//                    .filter(bottomWear -> bottomWear.getId() == (ratedProduct.getIdp()))
//                    .findFirst()
//                    .orElse(null);
//
//            double rating = (double) ratedProduct.getRating();
//            double weight = rating / userRatedProducts.stream().mapToInt(UserRating::getRating).sum();
//
//            if (matchedProduct != null) {
//                addToPreferenceWeights(bottomWearPreferenceWeights, "Style", matchedProduct.getStyle(), weight);
//                addToPreferenceWeights(bottomWearPreferenceWeights, "Fit", matchedProduct.getFit(), weight);
//                addToPreferenceWeights(bottomWearPreferenceWeights, "Material", matchedProduct.getMaterial(), weight);
//                addToPreferenceWeights(bottomWearPreferenceWeights, "Fabric", matchedProduct.getFabric(), weight);
//                addToPreferenceWeights(bottomWearPreferenceWeights, "Pattern", matchedProduct.getPattern(), weight);
//                addToPreferenceWeights(bottomWearPreferenceWeights, "Color", matchedProduct.getColor(), weight);
//            }
//        }
//
//        return bottomWearPreferenceWeights;
//    }
//
//    private void addToPreferenceWeights(Map<String, Map<String, Double>> preferenceWeights, String category, String value, double weight) {
//        if (preferenceWeights.get(category).containsKey(value)) {
//            preferenceWeights.get(category).put(value, preferenceWeights.get(category).get(value) + weight);
//        } else {
//            preferenceWeights.get(category).put(value, weight);
//        }
//    }
//
//    private Map<String, Map<String, Double>> createDefaultPreferenceWeights() {
//        Map<String, Map<String, Double>> preferenceWeights = new HashMap<>();
//
//        preferenceWeights.put("Style", new HashMap<>());
//        preferenceWeights.get("Style").put("default", 0.0);
//
//        preferenceWeights.put("Fit", new HashMap<>());
//        preferenceWeights.get("Fit").put("default", 0.0);
//
//        preferenceWeights.put("Material", new HashMap<>());
//        preferenceWeights.get("Material").put("default", 0.0);
//
//        preferenceWeights.put("Fabric", new HashMap<>());
//        preferenceWeights.get("Fabric").put("default", 0.0);
//
//        preferenceWeights.put("Pattern", new HashMap<>());
//        preferenceWeights.get("Pattern").put("default", 0.0);
//
//        preferenceWeights.put("Color", new HashMap<>());
//        preferenceWeights.get("Color").put("default", 0.0);
//
//        return preferenceWeights;
//    }

    //top wear



// calculateProductWeight and calculateProductWeightBottomWear methods go here...


    private double calculateProductWeight(TopWear product, Map<String, Map<String, Double>> productsPreferenceWeights) {
        double weight = 0.0;

        if (preferenceContainsValue(productsPreferenceWeights, "Style", product.getStyle()))
            weight += productsPreferenceWeights.get("Style").get(product.getStyle());

        if (preferenceContainsValue(productsPreferenceWeights, "Fit", product.getFit()))
            weight += productsPreferenceWeights.get("Fit").get(product.getFit());

        if (preferenceContainsValue(productsPreferenceWeights, "Material", product.getMaterial()))
            weight += productsPreferenceWeights.get("Material").get(product.getMaterial());

        if (preferenceContainsValue(productsPreferenceWeights, "Fabric", product.getFabric()))
            weight += productsPreferenceWeights.get("Fabric").get(product.getFabric());

        if (preferenceContainsValue(productsPreferenceWeights, "Pattern", product.getPattern()))
            weight += productsPreferenceWeights.get("Pattern").get(product.getPattern());

        if (preferenceContainsValue(productsPreferenceWeights, "Color", product.getColor()))
            weight += productsPreferenceWeights.get("Color").get(product.getColor());

        return weight;
    }

    private double calculateProductWeightBottomWear(BottomWear product, Map<String, Map<String, Double>> productsPreferenceWeights) {
        double weight = 0.0;

        if (preferenceContainsValue(productsPreferenceWeights, "Style", product.getStyle()))
            weight += productsPreferenceWeights.get("Style").get(product.getStyle());

        if (preferenceContainsValue(productsPreferenceWeights, "Fit", product.getFit()))
            weight += productsPreferenceWeights.get("Fit").get(product.getFit());

        if (preferenceContainsValue(productsPreferenceWeights, "Material", product.getMaterial()))
            weight += productsPreferenceWeights.get("Material").get(product.getMaterial());

        if (preferenceContainsValue(productsPreferenceWeights, "Fabric", product.getFabric()))
            weight += productsPreferenceWeights.get("Fabric").get(product.getFabric());

        if (preferenceContainsValue(productsPreferenceWeights, "Pattern", product.getPattern()))
            weight += productsPreferenceWeights.get("Pattern").get(product.getPattern());

        if (preferenceContainsValue(productsPreferenceWeights, "Color", product.getColor()))
            weight += productsPreferenceWeights.get("Color").get(product.getColor());

        return weight;
    }

    private boolean preferenceContainsValue(Map<String, Map<String, Double>> preferences, String category, String value) {
        return preferences.containsKey(category) && preferences.get(category).containsKey(value);
    }


}



