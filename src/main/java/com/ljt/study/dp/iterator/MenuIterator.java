package com.ljt.study.dp.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2019-12-14 17:30
 */
public class MenuIterator {

    public static void main(String[] args) {
        PancakeHouseMenu houseMenu = new PancakeHouseMenu();
        DinerMenu dinerMenu = new DinerMenu();
        List<Menu> menus = new ArrayList<>(2);
        menus.add(houseMenu);
        menus.add(dinerMenu);

        Waitress waitress = new Waitress(menus);
        waitress.printMenu();
    }

    private static class Waitress {

        private List<Menu> menus;

        public Waitress(List<Menu> menus) {
            super();
            this.menus = menus;
        }

        public void printMenu() {

            for (Menu menu : this.menus) {
                printMenu(menu.iterator());
            }
        }

        private void printMenu(Iterator<MenuItem> iterator) {
            while (iterator.hasNext()) {
                MenuItem menuItem = iterator.next();
                System.out.print(menuItem.getName() + ", ");
                System.out.print(menuItem.getPrice() + " -- ");
                System.out.println(menuItem.getDescription());
            }
        }
    }

    interface Menu {

        Iterator<MenuItem> iterator();

    }

    private static class MenuItem {

        private String name;
        private String description;
        private boolean vegetarian;
        private double price;

        public MenuItem(String name, String description, boolean vegetarian, double price) {
            super();
            this.name = name;
            this.description = description;
            this.vegetarian = vegetarian;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public boolean isVegetarian() {
            return vegetarian;
        }

        public double getPrice() {
            return price;
        }
    }

    private static class DinerMenu implements Menu {

        private static final int MAX_ITEMS = 6;
        private int numberOfItems = 0;
        private MenuItem[] menuItems;

        public DinerMenu() {
            super();
            this.menuItems = new MenuItem[MAX_ITEMS];

            this.addItem("Vegetarian BLT", "(Fakin') Bacon with lettuce & tomato on whole wheat", true, 2.99);
            this.addItem("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99);
            this.addItem("Soup of the day", "Soup of the day, with a side ofpotato salad", false, 3.29);
            this.addItem("Hotdog", "A hot dog, with saurkraut, relish, onions, topped with cheese", false, 3.05);
        }

        public void addItem(String name, String description, boolean vegetarian, double price) {
            MenuItem menuItem = new MenuItem(name, description, vegetarian, price);

            if (this.numberOfItems > MAX_ITEMS) {
                System.out.println("Sorry, menu is full! Can't add item to menu");
            } else {
                this.menuItems[this.numberOfItems++] = menuItem;
            }
        }

        public MenuItem[] getMenuItems() {
            return menuItems;
        }

        @Override
        public Iterator<MenuItem> iterator() {
            return new Itr();
        }

        private class Itr implements Iterator<MenuItem> {

            private int position = 0;

            @Override
            public boolean hasNext() {
                return this.position < menuItems.length && menuItems[position] != null;
            }

            @Override
            public MenuItem next() {
                if (this.position < menuItems.length) {
                    return menuItems[position++];
                }

                return null;
            }

            @Override
            public void remove() {
                if (this.position <= 0) {
                    throw new IllegalStateException("You can't rmove an item nutil you've done at least one next()");
                }

                if (menuItems[position - 1] != null) {
                    if (menuItems.length - 1 - position - 1 >= 0)
                        System.arraycopy(menuItems, position - 1 + 1, menuItems, position - 1, menuItems.length - 1 - position - 1);

                    menuItems[menuItems.length - 1] = null;
                }
            }
        }
    }

    private static class PancakeHouseMenu implements Menu {

        private List<MenuItem> menuItems;

        public PancakeHouseMenu() {
            super();
            this.menuItems = new ArrayList<>();

            this.addItem("K&B 's Pancake BreakFast", "Pancakes with fried eggs, sausage", true, 2.99);
            this.addItem("Regular Pancake BreakFast", "Pancakes with fried eggs, sausage", false, 2.99);
            this.addItem("Blueberry Pancake", "Pancakes made with fresh blueberries", true, 3.49);
            this.addItem("Waffles", "Waffles, with your choice of blueberries or strawberries", false, 3.59);
        }

        public void addItem(String name, String description, boolean vegetarian, double price) {
            MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
            this.menuItems.add(menuItem);
        }

        public List<MenuItem> getMenuItems() {
            return menuItems;
        }

        @Override
        public Iterator<MenuItem> iterator() {
            return this.menuItems.iterator();
        }
    }

}
