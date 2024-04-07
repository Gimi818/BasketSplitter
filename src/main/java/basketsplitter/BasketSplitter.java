package basketsplitter;

import java.util.*;

class BasketSplitter {

    private final Map<String, List<String>> deliveryOptions;

    BasketSplitter(String absolutePathToConfigFile) {
        this.deliveryOptions = ConfigLoader.loadConfig(absolutePathToConfigFile);
    }

    public Map<String, List<String>> split(List<String> items) {
        if (items == null) {
            throw new IllegalArgumentException("Item list cannot be null");
        }
        Map<String, List<String>> deliveryGroups = new HashMap<>();
        Set<String> remainingItems = new HashSet<>(items);

        Map<String, Integer> deliveryMethodCount = countDeliveryMethods(remainingItems);

        while (!remainingItems.isEmpty()) {
            String maxMethod = findMaxDeliveryMethod(deliveryMethodCount);
            List<String> maxGroup = filterAndUpdateDeliveryCounts(remainingItems, maxMethod, deliveryMethodCount);

            deliveryGroups.put(maxMethod, maxGroup);
            deliveryMethodCount.remove(maxMethod);
        }

        return deliveryGroups;
    }

    List<String> filterAndUpdateDeliveryCounts(Set<String> remainingItems, String method, Map<String, Integer> deliveryMethodCount) {
        List<String> maxGroup = new ArrayList<>();
        Iterator<String> iterator = remainingItems.iterator();

        while (iterator.hasNext()) {
            String item = iterator.next();
            List<String> options = deliveryOptions.getOrDefault(item, Collections.emptyList());
            if (options.contains(method)) {
                maxGroup.add(item);
                iterator.remove();

                options.forEach(opt -> {
                    deliveryMethodCount.put(opt, deliveryMethodCount.getOrDefault(opt, 0) - 1);
                    if (deliveryMethodCount.get(opt) <= 0) {
                        deliveryMethodCount.remove(opt);
                    }
                });
            }
        }

        return maxGroup;
    }

    Map<String, Integer> countDeliveryMethods(Set<String> items) {
        Map<String, Integer> deliveryMethodCount = new HashMap<>();
        items.forEach(item -> deliveryOptions.getOrDefault(item, Collections.emptyList())
                .forEach(method -> deliveryMethodCount.merge(method, 1, Integer::sum)));
        return deliveryMethodCount;
    }

    String findMaxDeliveryMethod(Map<String, Integer> deliveryMethodCount) {
        return Collections.max(deliveryMethodCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
