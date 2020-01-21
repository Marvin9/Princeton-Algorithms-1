# percolation
Princeton algorithm-1 week 1 assignment. 

## [Specifications](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)

![Percolation case study](https://introcs.cs.princeton.edu/java/24percolation/images/percolates-yes.png)

### nxn grid contains 
- blocked sites (black boxes)
- open sites (white boxes) 
- full sites (any open site which is indirectly connected to any of the top row's open site)

## Use
- Download [.jar](https://github.com/kevin-wayne/algs4) to use weighted quick union. 

- ## API

  - ***Constructor(int n) :***
    - It will create nxn grid.
    
    ``` java
    Percolates p1 = new Percolates(5);
    ```
   
  - ***void open(int row, int column)***
    - row and column value should be in the range of 1 to n.
    - opens a site at given block of grid.
    - connects neighbour open sites.
    
    ``` java
    p1.open(1, 2);
    p1.open(4, 5);
    ```
   
  - ***boolean percolates()***
    - checks for any full site of bottom row.
    - if it exists then grid percolates.
    
    ``` java
    p1.percolates();
    ```
