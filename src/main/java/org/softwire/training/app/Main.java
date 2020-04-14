package org.softwire.training.app;

import org.flywaydb.core.Flyway;
import org.softwire.training.models.Cat;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        Flyway flyway = Flyway.configure().dataSource(
                "jdbc:mysql://localhost:3306/cat_shelter?serverTimezone=UTC",
                "root",
                "jellyfish_123").load();
        flyway.migrate();

        displayOwnersDirectFromDatabase();
        displayCatsAndOwnersWithHql();
        displayCatWithSql();
        displayCatWithoutSql();
        updateCatWithSql();
        updateCatWithoutSql();
    }

    private static void displayOwnersDirectFromDatabase() throws SQLException {
        System.out.println("Direct access owners");
        List<String> ownerNames = new DirectAccess().getAllOwnerNames();
        System.out.println(ownerNames);
        System.out.println();
    }

    private static void displayCatsAndOwnersWithHql() {
        System.out.println("ORM access with HQL");
        List<Cat> cats = new OrmAccess().getAllCatsWithHql();
        System.out.println(cats);
        System.out.println(Arrays.toString(cats.stream().map(cat -> cat.getOwner().getName()).toArray()));
        System.out.println(cats.get(2).getOwner() == cats.get(3).getOwner());
        System.out.println();
    }

    private static void displayCatWithSql() {
        System.out.println("ORM access with SQL");
        System.out.println(new OrmAccess().getCatWithSql(1));
        System.out.println();
    }

    private static void displayCatWithoutSql() {
        System.out.println("ORM access without SQL");
        Cat cat = new OrmAccess().getCatWithoutSql(2);
        System.out.println(cat);
        System.out.println();
    }

    private static void updateCatWithSql() {
        System.out.println("ORM update with SQL");
        OrmAccess ormAccess = new OrmAccess();
        Cat cat = new OrmAccess().getCatWithoutSql(3);
        System.out.println(cat);
        cat.setAge(cat.getAge() + 1);
        ormAccess.updateCatWithSql(cat);
        System.out.println(ormAccess.getCatWithoutSql(3));
    }

    private static void updateCatWithoutSql() {
        System.out.println("ORM update without SQL");
        OrmAccess ormAccess = new OrmAccess();
        Cat cat = new OrmAccess().getCatWithoutSql(2);
        System.out.println(cat);
        cat.setAge(cat.getAge() + 1);
        ormAccess.updateCatWithoutSql(cat);
        System.out.println(ormAccess.getCatWithoutSql(2));
    }
}