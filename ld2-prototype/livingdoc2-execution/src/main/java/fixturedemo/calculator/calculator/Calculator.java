package fixturedemo.calculator.calculator;

import info.novatec.testit.livingdoc2.perform.annotations.BeforeRow;
import info.novatec.testit.livingdoc2.reflection.annotations.Alias;
import info.novatec.testit.livingdoc2.reflection.annotations.FixtureClass;


@FixtureClass({ "Rechner", "Taschenrechner", "Maths" })
public class Calculator {
    private int x;
    private int y;

    @BeforeRow
    public void clearFields() {
        x = 0;
        y = 0;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Alias("summe")
    public int sum() {
        return x + y;
    }

    @Alias("Differenz")
    public int difference() {
        return x - y;
    }

    @Alias({ "Produkt", "Mal" })
    public int product() {
        return x * y;
    }

    public int quotient() {
        return x / y;
    }
}
