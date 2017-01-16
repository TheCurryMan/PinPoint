#Inspiration
Navigating a large supermarket can be difficult and time-consuming. Even though there are aisle descriptions, they are often vague and it's impossible to remember where each category of products is located.

#What it does
Using various APIs and algorithms, our app generates a store layout bitmap and animates the path that the consumer should take to minimize the time spent at the store and to pick up all groceries.

#How we built it
##Bitmap
As we didn't have access to an actual store layout, we created a mock bitmap in excel with 0's representing free space and 1's representing obstacles such as aisles, cashier counters etc. We then translated this into a bitmap of the whole store layout in Java.

##Pathfinding
Our app uses an existing database from Supermarket API which when given a product name returns aisle number and other product information for many prominent retailers. Using information about the location such as "Aisle 11 Back" we would then plot an exact coordinate on the bitmap that fills within the bounds of Aisle 11 back. After adding all our products to the list and having all the bitmap coordinates, we then had to find the most optimal route between all the points. This is essentially a different use case for the Traveling Salesman Problem except starting and ending point are the same, so using a slightly modified genetic algorithm we calculated the most efficient (or close to most efficient) route. Because the algorithm did not account for obstacles between the positions of the products, we implemented the A* algorithm on the bitmap matrix to find the best path between the individual points to avoid aisle and other obstacles.

##Firebase and UI
After adding an item to the grocery list and clicking the start button to map the path, the Supermarket API searches for that item's aisle number. The aisle number is a key for the possible coordinates within that aisle and a "best fit" position is returned. A major part of this app was designing the UI as in 8bit even though the animation was functional it was hard to interpret the store layout and where to go. We added numerous features to clarify this, such as animating the current path to the closest item, adding a pulse for the item we need to go to, and varying the sizes of the item points. See image above.

#Challenges we ran into
Pathfinding algorithms!!!! We had no clue where to start with this except that one of our members had some prior experience with TSP and we had heard of A*. Understanding how exactly these algorithms worked and how to integrate it took a lot of time. We spent at least 40% of our time designing the UI. Figuring out how to represent the store layout and then actually making the bitmap took some time as well.

#Accomplishments that we're proud of
Functional app that looks nice!

#What we learned
A lot! Android development (We hadn't really developed in Android only knew Java from CS class and had some experience with Studio), Pathfinding Algorithms, Firebase integration.

#What's next for PinPoint
Integrating the shopping list with another app such as Evernote so consumers can import lists. Actually finding real store layouts (they exist somewhere) and figuring out how to efficiently create bitmaps of each.
