# BasketSplitter 
## System responsible for separating items from the basket into delivery types
 - The ConfigLoader class is responsible for loading and parsing configuration data from a specified file into a Map<String, List<String>> structure.
Its loadConfig method reads the file content, converts it from JSON format, and returns the structured data, while also logging the operation's success or failure.

- The BasketSplitter class is responsible for organizing a collection of items into groups based on their designated delivery methods, leveraging configuration data to determine the available delivery options for each item. It automates the process of segregating items for optimized delivery planning by identifying and applying the most prevalent delivery method across the items, ensuring each item is assigned to the most suitable delivery group.
  - Method "split": Segregates a list of items into groups based on their delivery methods by calculating the most common delivery method available, filtering items accordingly, and updating the count of delivery methods for the remaining items. It throws IllegalArgumentException if the input list is null.

  - Method "filterAndUpdateDeliveryCounts": Filters items from the remaining set that can be delivered using the specified method, updates the count of how often each delivery method appears, and removes the filtered items from the remaining set.

  - Method "countDeliveryMethods": Calculates and returns a map where keys are delivery methods and values are counts of how many items can be delivered using each method, based on the initial list of items.

  - Method "findMaxDeliveryMethod ": Identifies and returns the delivery method with the highest count of available items, helping to prioritize which delivery method to process next in the grouping algorithm.
