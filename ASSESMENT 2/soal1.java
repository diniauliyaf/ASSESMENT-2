import java.util.*;

class City {
    String name;

    public City(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Graph {
    private Map<City, List<City>> adjCities = new HashMap<>();

    public void addCity(City city) {
        adjCities.putIfAbsent(city, new ArrayList<>());
    }

    public void addEdge(City city1, City city2) {
        adjCities.get(city1).add(city2);
        adjCities.get(city2).add(city1); // assuming undirected graph
    }

    public List<City> getAdjCities(City city) {
        return adjCities.get(city);
    }

    public Set<City> getCities() {
        return adjCities.keySet();
    }
}

public class soal1 {

    private static void nearestNeighborTraversal(Graph graph, City start) {
        Set<City> visited = new HashSet<>();
        List<City> tour = new ArrayList<>();
        City current = start;
        visited.add(current);
        tour.add(current);

        while (visited.size() < graph.getCities().size()) {
            City nextCity = null;
            for (City neighbor : graph.getAdjCities(current)) {
                if (!visited.contains(neighbor)) {
                    nextCity = neighbor;
                    break;
                }
            }
            if (nextCity != null) {
                visited.add(nextCity);
                tour.add(nextCity);
                current = nextCity;
            } else {
                break; // no unvisited neighbors left
            }
        }

        System.out.println("Tour based on nearest neighbor traversal:");
        for (City city : tour) {
            System.out.println(city.name);
        }
    }

    private static void sortedByNameTraversal(List<City> cities, City start) {
        cities.sort(Comparator.comparing(city -> city.name));

        System.out.println("\nTour sorted by name from " + start.name + ":");
        for (City city : cities) {
            System.out.println(city.name);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of cities:");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        List<City> cities = new ArrayList<>();
        Graph graph = new Graph();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter city name:");
            String name = scanner.nextLine();
            City city = new City(name);
            cities.add(city);
            graph.addCity(city);
        }

        System.out.println("Enter number of edges:");
        int e = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        for (int i = 0; i < e; i++) {
            System.out.println("Enter two city names to connect with an edge:");
            String city1Name = scanner.nextLine();
            String city2Name = scanner.nextLine();
            City city1 = cities.stream().filter(c -> c.name.equals(city1Name)).findFirst().orElse(null);
            City city2 = cities.stream().filter(c -> c.name.equals(city2Name)).findFirst().orElse(null);
            if (city1 != null && city2 != null) {
                graph.addEdge(city1, city2);
            } else {
                System.out.println("Invalid city names. Please try again.");
                i--; // repeat this edge input
            }
        }

        System.out.println("Enter the starting city name (e.g., Paris):");
        String startCityName = scanner.nextLine();
        City startCity = cities.stream().filter(c -> c.name.equals(startCityName)).findFirst().orElse(null);
        if (startCity == null) {
            System.out.println(startCityName + " is not in the list of cities. Exiting...");
            return;
        }

        // a. Nearest Neighbor Approach
        nearestNeighborTraversal(graph, startCity);

        // b. Sorted by Name Approach
        sortedByNameTraversal(cities, startCity);
    }
}
