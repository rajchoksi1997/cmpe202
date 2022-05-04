import model.*;
import model.Cards;
import model.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Billing {
    public static void main(String[] args) {
        System.out.println("Hello world");

        // read inventory file
        String inventoryFileName = "src/inventory.csv";
        Inventory inventory = Inventory.getInstance();
        Cards cards = Cards.getInstance();
        try{
            System.out.println("Processing model.Inventory.csv file");
            List<List<String>> inventoryRecords = new ReadCsvFiles().readFile(inventoryFileName);
            System.out.println(inventoryRecords);


            int index = inventory.buildInventory(inventoryRecords);
            System.out.println("index->"+index);
            inventory.showInventory();

            // now we build cards dataset

            cards.addCards(index, inventoryRecords);
            System.out.println("done Processing model.Inventory.csv file");
        }catch (Exception exception){
            System.out.println("Error->"+exception.getMessage());
        }

        // read input file
        Cart cart = Cart.getInstance();
        try {
            System.out.println("processing Input cart");
            String cartFileName = "src/Input.csv";

            List<List<String>> cartRecords = new ReadCsvFiles().readFile(cartFileName);


            cart.buildCart(cartRecords);
            cart.showCart();


            // process cart to validate cart



        }catch (Exception exception){

        }

        //check cart--validations
        int essentials=0;
        int misc = 0;
        int luxury = 0;
        boolean invalid = false;
        if(cart !=null && inventory != null){
            List<CartItems> cartItem = cart.getCartItemsList();
            double card = cart.getCardNumber();
            HashMap<String, InventoryItems> inventoryList= inventory.getInventory();
            Bill bill = new Bill();
            List<BillItems> billItems = bill.getItems();
            for(CartItems item :cartItem ){
                double amount =0;
                String name = item.getItemName();
                int quantity = item.getQuantity();
                InventoryItems inventoryData = inventoryList.getOrDefault(name,null);
                if(inventoryData != null){
                    String category = inventoryData.getCategory();
                    int availableQuantity = inventoryData.getQuantity();
                    if(availableQuantity < quantity
                            || (category.equalsIgnoreCase("essentials") && essentials>3)
                            ||(category.equalsIgnoreCase("misc") && essentials>6)
                            ||(category.equalsIgnoreCase("luxury") && essentials>4)){
                        invalid = true;
                    }else{
                        if(category.equalsIgnoreCase("essentials")) essentials++;
                        else if(category.equalsIgnoreCase("misc")) misc++;
                        else if(category.equalsIgnoreCase("misc")) luxury++;
                        amount = amount + quantity*inventoryData.getPrice();
                        BillItems billItem = new BillItems(name,quantity,amount);
                        billItems.add(billItem);

                        availableQuantity = inventoryData.getQuantity()-quantity;
                        inventoryData.setQuantity(availableQuantity);
                        inventoryList.put(name,inventoryData);

                    }
                }else{
                    invalid = true;
                }

            }
            if(!invalid){
                bill.setItems(billItems);

            }else{

            }

        }

    }



}
