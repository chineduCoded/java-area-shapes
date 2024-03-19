import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ShapeAreaCalculator {

    public static void main(String[] args) {
        String inputFile = "input.csv";
        String outputFile = "output.txt";

        try (Scanner scanner = new Scanner(new File(inputFile));
                PrintWriter writer = new PrintWriter(outputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(","); // Split line by comma

                if (parts.length < 2) {
                    writer.println("Invalid line format in input file");
                    continue;
                }

                String shape = parts[0].trim();
                Shape shapeObject;
                try {
                    shapeObject = createShape(shape, parts);
                } catch (InvalidShapeException e) {
                    writer.println(e.getMessage());
                    continue;
                }

                double area = shapeObject.calculateArea();
                writer.println(String.format("Area of %s: %.2f", shape, area));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found: " + inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Shape createShape(String shape, String[] parts) throws InvalidShapeException {
        switch (shape.toLowerCase()) {
            case "rectangle":
                if (parts.length != 3) {
                    throw new InvalidShapeException("Rectangle requires length and width");
                }
                return new Rectangle(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
            case "circle":
                if (parts.length != 2) {
                    throw new InvalidShapeException("Circle requires radius");
                }
                return new Circle(Double.parseDouble(parts[1]));
            case "cuboid":
                if (parts.length != 4) {
                    throw new InvalidShapeException("Cuboid requires length, width, and height");
                }
                return new Cuboid(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]));
            case "triangle":
                if (parts.length != 3) {
                    throw new InvalidShapeException("Triangle requires base and height");
                }
                return new Triangle(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
            default:
                throw new InvalidShapeException("Invalid shape type: " + shape);
        }
    }
}

class InvalidShapeException extends Exception {
    public InvalidShapeException(String message) {
        super(message);
    }
}

interface Shape {
    double calculateArea();
}

class Rectangle implements Shape {
    private final double length;
    private final double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }
}

class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }
}

class Cuboid implements Shape {
    private final double length;
    private final double width;
    private final double height;

    public Cuboid(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 2 * (length * width + width * height + length * height);
    }
}

class Triangle implements Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}