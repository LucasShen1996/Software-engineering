package main;

import controller.Manage;
import entity.*;

import java.util.regex.Pattern;
import java.util.*;

import static controller.Manage.*;
import static util.FormatRow.*;
import static util.FormatRow.formatOneTable;
import static util.ProcessDate.*;
import static util.VerifyString.stringIsInteger;
import static util.VerifyString.stringLetter;


public class BakeShop {

    private static Staff user;
    private static Shop shop;
    private static Manage manage = new Manage();
    public final static int TOTALLENGTH = 150;

    public static void main(String[] args) {
        boolean flag = true;
        user = login();

        Scanner scanner = new Scanner(System.in);

        while (flag) {
            if ("Owner".equals(user.getType()) || "Manager".equals(user.getType())) {
                mainMenu();
                System.out.println("Please input the number of option: ");
                String option = scanner.nextLine().trim();
                if (!stringIsInteger(option)){
                    System.out.println("Please only input number: ");
                    continue;
                } else if (!option.equals("2") && !option.equals("1")){
                    System.out.println("Please only input '1' and '2'");
                    continue;
                } else if (stringIsInteger(option) && option.length() > 4){
                    System.out.println("Please only input '1' and '2'");
                    continue;
                }
                switch (Integer.parseInt(option)) {
                    case 1:
                        selectShopByStaffInterface(user);
                        shop = chooseOneShop(user);
                        while (flag) {
                            String shopOption = shopOptionMenu();
                            switch (shopOption) {
                                case "1": //1.Orders details
                                    while (flag) {
                                        String orderOption = orderOptionMenu();
                                        switch (orderOption) {
                                            case "1": //1. read and view order
                                                showOrderInterface(shop);
                                                break;
                                            case "2": //2. create order
                                                createOrderInterface(user, shop);
                                                break;
                                            case "3": //3. modify order
                                                updateOrderInterface(shop);
                                                break;
                                            case "4": //4. delete order
                                                deleteOrderByIdInterface();
                                                break;
                                            case "5": //5. check order's status
                                                checkOrderByOrderId();
                                                break;
                                            case "6": //6. archived orders
                                                showAchievedOrderInterface(shop);
                                                break;
                                            case "7": //7. add new items
                                                createItemInterface(shop);
                                                break;
                                            case "8": //8.log out
                                                flag = logout();
                                                break;
                                        }
                                        if ("back".equals(orderOption)) {
                                            break;
                                        }
                                    }
                                    break;
                                case "2": //2.Staffs details
                                    while (true) {
                                        String staffOption = staffOptionMenu();
                                        switch (staffOption) {
                                            case "1": //1. Read and view staffs details
                                                selectStaffByShopInterface(shop);
                                                break;
                                            case "2": //2. Add a new staff
                                                createStaffInterface(user);
                                                break;
                                            case "3": //3. Update a staff
                                                updateStaffInterface(shop);
                                                break;
                                            case "4": //4. Delete a staff
                                                deleteStaffByIdInterface(shop);
                                                break;
                                        }
                                        if ("back".equals(staffOption)) {
                                            break;
                                        }
                                    }
                                    break;
                                case "3": //3.Weekly statement
                                    Date addDate = new Date();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(addDate);
                                    calendar.add(Calendar.DAY_OF_YEAR, -7);
                                    Date oldDate = calendar.getTime();
                                    showBusinessReportOneShop(shop, oldDate, addDate);
                                    break;
                                case "4": //4. Monthly statement
                                    Date addDate1 = new Date();
                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.setTime(addDate1);
                                    calendar1.add(Calendar.MONTH, -1);
                                    Date oldDate1 = calendar1.getTime();
                                    showRevenueReportOneShop(shop, oldDate1);
                                    break;
                                case "5"://log out
                                    flag = logout();
                                    break;
                            }
                            if ("back".equals(shopOption)) {
                                break;
                            }
                        }
                        break;
                    case 2:
                        flag = logout();
                        break;
                }
            } else {
                // Staff
                while (flag) {
                    shop = selectShopByStaff(user, manage.getShopList()).get(0);
                    String orderOption = orderOptionMenu();
                    switch (orderOption) {
                        case "1": //1. read and view order
                            showOrderInterface(shop);
                            break;
                        case "2": //2. create order
                            createOrderInterface(user, shop);
                            break;
                        case "3":
                            updateOrderInterface(shop);
                            break;
                        case "4": //4. delete order
                            deleteOrderByIdInterface();
                            break;
                        case "5": //5. check order's status
                            checkOrderByOrderId();
                            break;
                        case "6": //6. archived orders
                            showAchievedOrderInterface(shop);
                            break;
                        case "7": //7. add new items
                            createItemInterface(shop);
                            break;
                        case "8": //8. add new items
                            flag = logout();
                            break;
                    }
                }
            }
        }
    }

    public static void mainMenu() {
        int totalLength = TOTALLENGTH;
        topInterface(totalLength);
        String hello = "Hi " + user.getName() + ", welcome to use bakeshop system!";
        System.out.println(hello);
        totalLength = hello.length() < totalLength ? totalLength : hello.length();
        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Menu", totalLength, "centre"));
        System.out.println(formatOneRow("1. My Shops", totalLength, "left"));
        System.out.println(formatOneRow("2. Logout", totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));
    }

    //LOGIN
    public static Staff login() {
        Staff staff = new Staff();
        int totalLength = TOTALLENGTH;
        topInterface(totalLength);
        while (true) {
            System.out.println("Please input email: ");
            Scanner scanner = new Scanner(System.in);
            String email = scanner.nextLine().trim();
            //The format of the email when you login
            while (!Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$").matcher(email).matches()){
                System.out.println("Please input 'xxx@xxx.xxx' ");
                email = scanner.nextLine().trim();
            }
            while (true) {
                if (null != manage.selectStaffByEmail(email)) {
                    staff = manage.selectStaffByEmail(email);

                    while (true) {
                        System.out.println("Please input password: ");
                        String psw = scanner.nextLine().trim();
                        if (psw.equals(staff.getPassword())) {
                            System.out.println("Login successfully!");
                            return staff;
                        } else {
                            System.out.println("Your password is not right!");
                        }
                    }
                } else {
                    System.out.println("Your email is invalid!");
                    break;
                }
            }
        }
    }

    // create a staff menu: 1.read and view 2.add 3.update 4.delete
    public static String staffOptionMenu() {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);
        topInterface(totalLength);
        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Staff Menu", totalLength, "centre"));
        System.out.println(formatOneRow("1. Read and view staffs details", totalLength, "left"));
        System.out.println(formatOneRow("2. Add a new staff", totalLength, "left"));
        System.out.println(formatOneRow("3. Update a staff", totalLength, "left"));
        System.out.println(formatOneRow("4. Delete a staff", totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));

        while (true) {
            System.out.println("Please input the number of the option (input 'back' to previous menu): ");
            String option = scanner.nextLine().trim();
            //Determine if the input options are between 1 and 4
            if ("1".equals(option) || "2".equals(option) || "3".equals(option) || "4".equals(option) || "back".equals(option)) {
                return option;
            } else {
                System.out.println("The input must be a integer and between 1 to 4 or 'back'!");
            }
        }
    }

    // shop menu
    public static String shopOptionMenu() {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);
        topInterface(totalLength);
        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Menu", totalLength, "centre"));
        System.out.println(formatOneRow("1. Orders details", totalLength, "left"));
        System.out.println(formatOneRow("2. Staffs details", totalLength, "left"));
        System.out.println(formatOneRow("3. Weekly statement", totalLength, "left"));
        System.out.println(formatOneRow("4. Monthly statement", totalLength, "left"));
        System.out.println(formatOneRow("5. Log out", totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));

        //Determine if the input options are between 1 and 5
        while (true) {
            System.out.println("Please input the number of the option (input 'back' to previous menu): ");
            String option = scanner.nextLine().trim();
            if ("1".equals(option) || "2".equals(option) || "3".equals(option) || "4".equals(option) ||"5".equals(option) || "back".equals(option)) {
                return option;
            } else {
                System.out.println("The input must be a integer and between 1 to 5 or 'back'!");
            }
        }
    }

    // order menu
    public static String orderOptionMenu() {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);
        topInterface(totalLength);
        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Menu", totalLength, "centre"));
        System.out.println(formatOneRow("1. Read and View order", totalLength, "left"));
        System.out.println(formatOneRow("2. Create Order", totalLength, "left"));
        System.out.println(formatOneRow("3. Modify Order", totalLength, "left"));
        System.out.println(formatOneRow("4. Delete Order", totalLength, "left"));
        System.out.println(formatOneRow("5. Check Order's Status", totalLength, "left"));
        System.out.println(formatOneRow("6. Check Archived Orders", totalLength, "left"));
        System.out.println(formatOneRow("7. Add New Items", totalLength, "left"));
        System.out.println(formatOneRow("8. Log out", totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));

        ////Determine if the input options are between 1 and 8
        while (true) {
            System.out.println("Please input the number of the option (input 'back' to previous menu): ");
            String option = scanner.nextLine().trim();
            if ("1".equals(option) || "2".equals(option) || "3".equals(option) || "4".equals(option) || "5".equals(option) || "6".equals(option) || "7".equals(option) ||"8".equals(option) || "back".equals(option)) {
                return option;
            } else {
                System.out.println("The input must be a integer and between 1 to 8 or 'back'!");
            }
        }
    }

    // choose one shop
    public static Shop chooseOneShop(Staff user) {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Shop shop = null;
            System.out.println("Please input the id of the shop you want to check (input 'back' to previous menu): ");
            String shopId = scanner.nextLine();
            if (stringIsInteger(shopId) && shopId.length() <= 4 && !"".equals(shopId)) {
                List<Shop> userShopList = selectShopByStaff(user, manage.getShopList());
                shop = selectShopByShopId(Integer.parseInt(shopId), manage.getShopList());
                for (Shop shop1 : userShopList) {
                    if (null != shop){
                        if (shop1.getShopId() == shop.getShopId()) {
                            System.out.println("Hi " + user.getName() + ", welcome to shop " + shop.getShopId() + ".");
                            return shop;
                        }
                    }
                }
            } else if(stringIsInteger(shopId) && shopId.length() > 4){
                System.out.println("You do not belong to this shop!");
            } if ("".equals(shopId)){
                System.out.println("Your input can not be empty!");
            } else {
                System.out.println("Your input is invalid!");
            }
        }
    }

    //create order
    public static void createOrderInterface(Staff user, Shop shop) {
        List<Integer> stringLengthList = new ArrayList<Integer>();

        //table
        List<List> dataList = new ArrayList<List>();

        int totalLength = TOTALLENGTH;
        Order order = new Order();
        Scanner scanner = new Scanner(System.in);

        String date = getSystemDate();
        String time = getSystemTime();

        int totalCost = 0;
        int totalAmount = 0;
        List<String> itemList = new ArrayList<String>();
        while (true) {
            System.out.println("Please input the name and quantity of one item (e.g. milk,1): (input 'end' is to finish)");
            String itemNameAndQuantity = scanner.nextLine().trim();
            if ("end".equals(itemNameAndQuantity)) {
                if (itemList.size() == 0){
                    System.out.println("You must input at least one item!");
                } else {
                    break;
                }
            }
            //Input format for name and quantity of items
            else if (Pattern.compile("[a-zA-Z]+,\\d+").matcher(itemNameAndQuantity).matches()) {
                String[] itemStr = itemNameAndQuantity.split(",");
                for (int i = 0; i < manage.getItemList().size(); i++) {
                    Item item = selectItemByName(itemStr[0]);
                    //Determine if the item exists
                    if (null != item.getItemId()) {
                        //Determine if this item is available
                        if (item.getShopList().contains(shop)) {
                            // Determine if the stock is adequate
                            if ((int) item.getStock() >= Integer.parseInt(itemStr[1])) {
                                itemList.add(item.getName() + "," + itemStr[1]);
                                break;
                            } else {
                                System.out.println("The stock is not enough! Available number: " + (int) item.getStock());
                                break;
                            }
                        } else {
                            System.out.println("This item is not available in this shop!");
                            break;
                        }
                    } else {
                        System.out.println("This item does not exist!");
                        break;
                    }
                }
            } else {
                System.out.println("This input is invalid!");
            }
        }
        // Determine if the customer name entered is correct
        String customerName = "";
        while (true) {
            System.out.println("Please input the name of customer:");
            customerName = scanner.nextLine().trim();
            if ("".equals(customerName) || null == customerName) {
                System.out.println("The name of customer can not be empty!");
            } else if (!stringLetter(customerName)) {
                System.out.println("Please input letter as Name!");
            } else {
                break;
            }

        }
        //Determine if the input phone format is correct：empty OR (0x)xxxxxxxx
        String phoneNumber = "";
        while (true) {
            System.out.println("Please input the phone number of customer (empty is acceptable): ");
            phoneNumber = scanner.nextLine().trim();
            if (!"".equals(phoneNumber)) {
                if (!Pattern.compile("\\(0[1-9]\\)[0-9]{8}").matcher(phoneNumber).matches()) {
                    System.out.println("Please only input with this format '(0x)xxxxxxxx': ");
                    continue;
                }
            }
            break;
        }

        stringLengthList.add("order".length());
        String orderNum = "order number: " + (manage.getOrderList().get(manage.getOrderList().size() - 1).getOrderId() + 1);
        stringLengthList.add(orderNum.length());
        List<String> titleList = new ArrayList<String>();
        titleList.add("name of the item");
        titleList.add("total cost of the item");
        titleList.add("quantity");
        dataList.add(titleList);
        String orderCreate = "order created by: " + user.getName();
        stringLengthList.add(orderCreate.length());
        String orderDate = "order date: " + date;
        stringLengthList.add(orderDate.length());
        String orderTime = "order time: " + time;
        stringLengthList.add(orderTime.length());
        String orderStatus = "order status: " + "READY";
        stringLengthList.add(orderStatus.length());
        String custName = "customer name : " + customerName;
        stringLengthList.add(custName.length());
        String phone = "phone Num: " + phoneNumber;
        stringLengthList.add(phone.length());
        String orderType = "order type: " + ("".equals(phoneNumber) ? "normal" : "advance");
        stringLengthList.add(orderType.length());

        for (String item : itemList) {
            List<String> rowList = new ArrayList<String>();
            rowList.add(item.split(",")[0]);
            rowList.add((int) (selectItemByName(item.split(",")[0]).getPrice() * Integer.parseInt(item.split(",")[1])) + "");
            rowList.add(item.split(",")[1]);
            dataList.add(rowList);
            totalCost += selectItemByName(item.split(",")[0]).getPrice() * Integer.parseInt(item.split(",")[1]);
            totalAmount += Integer.parseInt(item.split(",")[1]);
        }
        String cost = "total cost: " + totalCost;
        stringLengthList.add(cost.length());
        List<Integer> dataLengthList = getEachCellLengthTable(dataList, titleList.size());
        int dataLength = 0;
        for (int i : dataLengthList) {
            dataLength += i;
        }

        stringLengthList.add(dataLength);
        totalLength = Collections.max(stringLengthList) + 2 + titleList.size() - 1;

        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("order", totalLength, "centre"));
        System.out.println(formatOneRow(orderNum, totalLength, "left"));
        System.out.println(formatOneTable(dataList, totalLength, titleList.size()));
        System.out.println(formatOneRow(orderCreate, totalLength, "left"));
        System.out.println(formatOneRow(orderDate, totalLength, "left"));
        System.out.println(formatOneRow(orderTime, totalLength, "left"));
        System.out.println(formatOneRow(orderStatus, totalLength, "left"));
        System.out.println(formatOneRow(custName, totalLength, "left"));
        System.out.println(formatOneRow(phone, totalLength, "left"));
        System.out.println(formatOneRow(orderType, totalLength, "left"));
        System.out.println(formatOneRow(cost, totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));


        order.setShop(shop);
        order.setOrderId(manage.getOrderList().get(manage.getOrderList().size() - 1).getOrderId() + 1);
        List<Item> itemLists = new ArrayList<Item>();
        for (String item : itemList) {
            itemLists.add(selectItemByName(item.split(",")[0]));
        }
        order.setItem(itemLists);

        List<Integer> priceLists = new ArrayList<Integer>();
        for (String item : itemList) {
            priceLists.add(Integer.parseInt(item.split(",")[1]));
        }
        order.setPrice(priceLists);

        List<Integer> quantityLists = new ArrayList<Integer>();
        for (String item : itemList) {
            quantityLists.add(Integer.parseInt(item.split(",")[1]));
        }
        order.setQuantity(quantityLists);
        order.setDate(combineDateAndTime(date, time));
        order.setStatus("READY");
        order.setStaff(user);
        order.setCustomerName(customerName);
        order.setCustomerPhone(phoneNumber);
        order.setTotalCost(totalCost);
        order.setType("".equals(phoneNumber) ? "normal" : "advance");
        createOrder(order);
        List<Order> orderList1 = manage.getOrderList();
        orderList1.add(order);
        manage.setOrderList(orderList1);

        for (String item : itemList) {
            Item item1 = selectItemByName(item.split(",")[0]);
            int stock = (int) item1.getStock();
            item1.setStock((int) stock - Integer.parseInt(item.split(",")[1]));
            updateInventoryByItem(item1);
            for (Item items : manage.getItemList()) {
                if (items.getName().equals(item.split(",")[0])) {
                    items.setStock(item1.getStock());
                }
            }
        }

    }

    // choose a staff and show the staff information
    public static void selectStaffByShopInterface(Shop shop) {
        int totalLength = TOTALLENGTH;
        topInterface(totalLength);

        List<Staff> staffShopList = selectStaffByShop(shop, manage.getStaffList());

        for (Staff staff : staffShopList) {
            System.out.println(loopAdd("=", totalLength));
            System.out.println(formatOneRow("Staff number: " + staff.getStaffId(), totalLength, "left"));
            System.out.println(formatOneRow("Name: " + staff.getName(), totalLength, "left"));
            System.out.println(formatOneRow("Email: " + staff.getEmail(), totalLength, "left"));
            System.out.println(formatOneRow("Password: " + staff.getPassword(), totalLength, "left"));
            System.out.println(formatOneRow("Staff type: " + staff.getType(), totalLength, "left"));
            System.out.println(formatOneRow("TFN: " + staff.getTFN(), totalLength, "left"));
            System.out.println(formatOneRow("Street: " + staff.getStreet(), totalLength, "left"));
            System.out.println(formatOneRow("City: " + staff.getCity(), totalLength, "left"));
            System.out.println(formatOneRow("State: " + staff.getState(), totalLength, "left"));
            System.out.println(formatOneRow("Postal: " + staff.getPostal(), totalLength, "left"));
            System.out.println(formatOneRow("Contact number: " + staff.getPhone(), totalLength, "left"));
            System.out.println(loopAdd("=", totalLength));
        }
    }

    // update staff information
    public static void updateStaffInterface(Shop shop) {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);
        Staff nowStaff = new Staff();
        Staff staff = new Staff();
        boolean suss = false;
        List<Staff> shopStaffList = selectStaffByShop(shop, manage.getStaffList());
        //Determine if the id entered is valid
        while (true) {
            System.out.println("Please input the ID(integer) of the staff: ");
            int staffId = 0;
            String staffIdStr = scanner.nextLine().trim();
            if (staffIdStr.isEmpty()) {
                System.out.println("Staff can not be empty");
            } else if (stringIsInteger(staffIdStr) && staffIdStr.length() > 4) {
                System.out.println("Input is too long");
            } else {
                if (stringIsInteger(staffIdStr)) {
                    staffId = Integer.parseInt(staffIdStr);
                    nowStaff = selectStaffByStaffId(staffId, shopStaffList);
                    if (null != nowStaff) {
                        break;
                    } else {
                        System.out.println("This staff does not exist in this shop!");
                    }
                } else {
                    System.out.println("This ID is invalid!");
                }
            }
        }

        staff.setStaffId(nowStaff.getStaffId());
        staff.setPhone(nowStaff.getPhone());
        staff.setPostal(nowStaff.getPostal());
        staff.setStatus(nowStaff.getStatus());
        staff.setName(nowStaff.getName());
        staff.setCity(nowStaff.getCity());
        staff.setStreet(nowStaff.getStreet());
        staff.setPassword(nowStaff.getPassword());
        staff.setEmail(nowStaff.getEmail());
        staff.setTFN(nowStaff.getTFN());
        staff.setState(nowStaff.getState());
        staff.setShoplist(nowStaff.getShoplist());
        staff.setType(nowStaff.getType());

        int option = 0;
        String content = "";
        while (true) {
            topInterface(totalLength);
            System.out.println(loopAdd("=", totalLength));
            System.out.println(formatOneRow("Staff Information", totalLength, "centre"));
            System.out.println(formatOneRow("Staff number: " + staff.getStaffId(), totalLength, "left"));
            System.out.println(formatOneRow("1.Name: " + staff.getName(), totalLength, "left"));
            System.out.println(formatOneRow("2.Email: " + staff.getEmail(), totalLength, "left"));
            System.out.println(formatOneRow("3.Password: " + staff.getPassword(), totalLength, "left"));
            System.out.println(formatOneRow("4.Staff type: " + staff.getType(), totalLength, "left"));
            System.out.println(formatOneRow("TFN: " + staff.getTFN(), totalLength, "left"));
            System.out.println(formatOneRow("5.Street: " + staff.getStreet(), totalLength, "left"));
            System.out.println(formatOneRow("6.City: " + staff.getCity(), totalLength, "left"));
            System.out.println(formatOneRow("7.State: " + staff.getState(), totalLength, "left"));
            System.out.println(formatOneRow("8.Postal: " + staff.getPostal(), totalLength, "left"));
            System.out.println(formatOneRow("9.Contact number: " + staff.getPhone(), totalLength, "left"));
            System.out.println(loopAdd("=", totalLength));

            System.out.println("Please input the option(integer) that you want to update ('submit' to confirm and 'back' to cancel): ");
            String optionStr = scanner.nextLine().trim();

            if (stringIsInteger(optionStr) && !"".equals(optionStr) && optionStr.length() <= 4) {
                option = Integer.parseInt(optionStr);
                if (option < 10 && option > 0) {
                    while (true) {
                        System.out.println("Please input the content that you want to update: ");
                        content = scanner.nextLine().trim();
                        switch (option) {
                            case 1:
                                //The format of the name entered
                                while (!stringLetter(content) || content.equals("")) {
                                    System.out.println("The name should be Alphabet: ");
                                    content = scanner.nextLine().trim();
                                }
                                staff.setName(content);
                                break;
                            case 2:
                                //The format of the email entered
                                while (!Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$").matcher(content).matches()){
                                    System.out.println("Please input 'xxx@xxx.xxx' ");
                                    content = scanner.nextLine().trim();
                                }
                                staff.setEmail(content);
                                break;
                            case 3:
                                //The format of the paw entered
                                while (content.equals("")) {
                                    content = scanner.nextLine().trim();
                                    System.out.println("Password can not be empty: ");
                                }
                                staff.setPassword(content);
                                break;
                            case 4:
                                //The format of the staff type entered
                                while (true) {
                                    if ("manager".equals(content.toLowerCase()) || "staff".equals(content.toLowerCase()) || "owner".equals(content.toLowerCase())) {
                                        staff.setType(content);
                                        break;
                                    } else {
                                        System.out.println("The type must be Manager, Staff, or Owner!");
                                        System.out.println("Please input the content that you want to update: ");
                                        content = scanner.nextLine().trim();
                                    }
                                }
                                break;
                            case 5:
                                //The format of the street name entered
                                while (!stringLetter(content) || content.equals("")) {
                                    System.out.println("Please re-input street name(Letter only): ");
                                    content = scanner.nextLine().trim();
                                }
                                staff.setStreet(content);
                                break;
                            case 6:
                                //The format of the city name entered
                                while (!stringLetter(content) || content.equals("")) {
                                    System.out.println("Please re-input city name(Letter only): ");
                                    content = scanner.nextLine().trim();
                                }
                                staff.setCity(content);
                                break;
                            case 7:
                                //The format of the state name entered
                                while (!stringLetter(content) || content.equals("")) {
                                    System.out.println("Please re-input state name(Letter only): ");
                                    content = scanner.nextLine().trim();
                                }
                                staff.setStatus(content);
                                break;
                            case 8:
                                //The format of the postal name entered
                                while (!stringIsInteger(content) || content.equals("")) {
                                    System.out.println("Please re-input postal name(Number only): ");
                                    content = scanner.nextLine().trim();
                                }
                                staff.setPostal(Integer.parseInt(content));
                                break;
                            case 9:
                                //The format of the phone number entered
                                while (!Pattern.compile("\\(0[1-9]\\)[0-9]{8}").matcher(content).matches() || content.equals("")) {
                                    System.out.println("Please only input with this format '(0x)xxxxxxxx': ");
                                    content = scanner.nextLine().trim();
                                }
                                staff.setPhone(content);
                                break;
                        }
                        break;
                    }
                } else {
                    System.out.println("Please input 1 - 9!");
                }
            } else if ("submit".equals(optionStr)) {
                for (int i = 0; i < manage.getStaffList().size(); i++) {
                    if (manage.getStaffList().get(i).getStaffId() == staff.getStaffId()) {
                        manage.getStaffList().set(i, staff);
                    }
                }
                suss = updateStaff(staff);
                break;
            } else if ("back".equals(optionStr)) {
                break;
            } else {
                System.out.println("This option is invalid!");
            }
        }
        if (suss) {
            System.out.println("Update Successfully!");
        } else {
            System.out.println("No update!");
        }
    }

    // update order
    public static void updateOrderInterface(Shop shop) {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);
        Order nowOrder = new Order();
        Order order = new Order();
        boolean succ = false;

        List<Order> shopOrderList = selectOrderByShop(shop, manage.getOrderList());

       // Determine if the id entered is valid
        while (true) {
            System.out.println("Please input the ID(integer) of the Order: ");
            int orderId = 0;
            String orderIdStr = scanner.nextLine().trim();
            if (stringIsInteger(orderIdStr) && !"".equals(orderIdStr)) {
                orderId = Integer.parseInt(orderIdStr);
                nowOrder = selectOrderByOrderId(orderId, shopOrderList);
                if (null != nowOrder) {
                    break;
                } else {
                    System.out.println("This order does not exist in this shop!");
                }
            } else {
                System.out.println("This ID is invalid!");
            }
        }


        order.setOrderId(nowOrder.getOrderId());
        order.setStaff(nowOrder.getStaff());
        order.setShop(nowOrder.getShop());
        order.setDate(nowOrder.getDate());
        order.setItem(nowOrder.getItem());
        order.setQuantity(nowOrder.getQuantity());
        order.setPrice(nowOrder.getPrice());
        order.setStatus(nowOrder.getStatus());
        order.setCustomerPhone(nowOrder.getCustomerPhone());
        order.setCustomerName(nowOrder.getCustomerName());
        order.setType(nowOrder.getType());
        order.setTotalCost(nowOrder.getTotalCost());

        // the list of order information
        while (true) {
            List<List> dataList = new ArrayList<List>();
            List<Integer> stringLengthList = new ArrayList<Integer>();
            stringLengthList.add("order".length());
            String orderNum = "order number: " + manage.getOrderList().get(manage.getOrderList().size() - 1).getOrderId();
            stringLengthList.add(orderNum.length());
            List<String> titleList = new ArrayList<String>();
            titleList.add("name of the item");
            titleList.add("total cost of the item");
            titleList.add("quantity");
            dataList.add(titleList);
            String orderCreate = "order created by: " + user.getName();
            stringLengthList.add(orderCreate.length());
            String orderDate = "order date: " + getDate(order.getDate());
            stringLengthList.add(orderDate.length());
            String orderTime = "order time: " + getTime(order.getDate());
            stringLengthList.add(orderTime.length());
            String orderStatus = "1. order status: " + order.getStatus();
            stringLengthList.add(orderStatus.length());
            String custName = "2. customer name : " + order.getCustomerName();
            stringLengthList.add(custName.length());
            String phone = "3. phone Num: " + order.getCustomerPhone();
            stringLengthList.add(phone.length());
            String orderType = "order type: " + ("".equals(order.getCustomerPhone()) ? "normal" : "advance");
            stringLengthList.add(orderType.length());

            if (order.getItem().get(0).getItemId() != null) {
                for (int i = 0; i < order.getItem().size(); i++) {
                    List<String> rowList = new ArrayList<String>();
                    rowList.add(order.getItem().get(i).getName());
                    rowList.add(order.getQuantity().get(i) + "");
                    rowList.add(order.getPrice().get(i) + "");
                    dataList.add(rowList);
                }
            } else {
                List<String> rowList = new ArrayList<String>();
                rowList.add("");
                rowList.add("");
                rowList.add("");
                dataList.add(rowList);
            }
            String cost = "total cost: " + order.getTotalCost();
            stringLengthList.add(cost.length());
            List<Integer> dataLengthList = getEachCellLengthTable(dataList, titleList.size());
            int dataLength = 0;
            for (int i : dataLengthList) {
                dataLength += i;
            }

            stringLengthList.add(dataLength);
            totalLength = Collections.max(stringLengthList) + 2 + titleList.size() - 1;

            System.out.println(loopAdd("=", totalLength));
            System.out.println(formatOneRow("order", totalLength, "centre"));
            System.out.println(formatOneRow(orderNum, totalLength, "left"));
            System.out.println(formatOneTable(dataList, totalLength, titleList.size()));
            System.out.println(formatOneRow(orderCreate, totalLength, "left"));
            System.out.println(formatOneRow(orderDate, totalLength, "left"));
            System.out.println(formatOneRow(orderTime, totalLength, "left"));
            System.out.println(formatOneRow(orderStatus, totalLength, "left"));
            System.out.println(formatOneRow(custName, totalLength, "left"));
            System.out.println(formatOneRow(phone, totalLength, "left"));
            System.out.println(formatOneRow(orderType, totalLength, "left"));
            System.out.println(formatOneRow(cost, totalLength, "left"));
            System.out.println(loopAdd("=", totalLength));

            System.out.println("Please input the option(integer) that you want to update ('submit' to confirm and 'back' to cancel): ");
            String optionStr = scanner.nextLine().trim();
            int option = 0;
            String content = "";

            if (stringIsInteger(optionStr) && !"".equals(optionStr) && optionStr.length() <= 4) {
                option = Integer.parseInt(optionStr);
                if (option == 1 || option == 2 || option == 3) {
                    while (true) {
                        System.out.println("Please input the content that you want to update: ");
                        content = scanner.nextLine().trim();
                        switch (option) {
                            case 1:
                                while (true){
                                    if ("achieved".equals(content.toLowerCase()) || "ready".equals(content.toLowerCase())) {
                                        order.setStatus(content);
                                        break;
                                    } else {
                                        System.out.println("The status should be 'achieved' or 'ready'!");
                                        System.out.println("Please input the content that you want to update: ");
                                        content = scanner.nextLine().trim();
                                    }
                                }
                                break;
                            case 2:
                                while (true) {
                                    if ("".equals(content) || null == content) {
                                        System.out.println("The name of customer can not be empty!");
                                    } else if (!stringLetter(content)) {
                                        System.out.println("Please input letter as Name!");
                                    } else {
                                        order.setCustomerName(content);
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                while (true) {
                                    if (!"".equals(order.getCustomerPhone())) {
                                        if (!Pattern.compile("\\(0[1-9]\\)[0-9]{8}").matcher(content).matches()) {
                                            System.out.println("Please only input with this format '(0x)xxxxxxxx': ");
                                        } else {
                                            order.setCustomerPhone(content);
                                            break;
                                        }
                                    } else {
                                        System.out.println("This order is a normal order, the phone number is empty!");
                                        break;
                                    }
                                }
                                break;
                        }
                        break;
                    }
                } else {
                    System.out.println("Please input 1 - 3:");
                }
            } else if ("submit".equals(optionStr)) {
                for (int i = 0; i < manage.getOrderList().size(); i++) {
                    if (manage.getOrderList().get(i).getOrderId() == order.getOrderId()) {
                        manage.getOrderList().set(i, order);
                    }
                }
                succ = updateOrder(order);
                break;
            } else if ("back".equals(optionStr)) {
                break;
            } else {
                System.out.println("This option is invalid!");
            }
        }
        if (succ) {
            System.out.println("Update Successfully!");
        } else {
            System.out.println("No update!");
        }


    }

    public static void deleteStaffByIdInterface(Shop shop) {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);
        Staff staff = new Staff();
        List<Staff> shopStaffList = selectStaffByShop(shop, manage.getStaffList());
        while (true) {
            System.out.println("Please input the ID(integer) of the staff you want to delete: ");
            String staffId = scanner.next();
            if (stringIsInteger(staffId)) {
                staff = selectStaffByStaffId(Integer.parseInt(staffId), shopStaffList);
                if (null != staff) {
                    break;
                } else {
                    System.out.println("This staff does not exist in this shop!");
                }
            } else {
                System.out.println("The ID of staff must be integer!");
            }
        }

        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Staff Information", totalLength, "centre"));
        System.out.println(formatOneRow("Staff number: " + staff.getStaffId(), totalLength, "left"));
        System.out.println(formatOneRow("Name: " + staff.getName(), totalLength, "left"));
        System.out.println(formatOneRow("Email: " + staff.getEmail(), totalLength, "left"));
        System.out.println(formatOneRow("Password: " + staff.getPassword(), totalLength, "left"));
        System.out.println(formatOneRow("Staff type: " + staff.getType(), totalLength, "left"));
        System.out.println(formatOneRow("TFN: " + staff.getTFN(), totalLength, "left"));
        System.out.println(formatOneRow("Street: " + staff.getStreet(), totalLength, "left"));
        System.out.println(formatOneRow("City: " + staff.getCity(), totalLength, "left"));
        System.out.println(formatOneRow("State: " + staff.getState(), totalLength, "left"));
        System.out.println(formatOneRow("Postal: " + staff.getPostal(), totalLength, "left"));
        System.out.println(formatOneRow("Contact number: " + staff.getPhone(), totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));

        boolean suss = false;
        while (true) {
            System.out.println("Please input 'submit' to confirm or 'back' to cancel: ");
            String option = scanner.next();
            if ("submit".equals(option)) {
                suss = deleteStaffById(staff);
                break;
            } else if ("back".equals(option)) {
                break;
            } else {
                System.out.println("You can only input 'submit' or 'back'!");
            }
        }
        if (suss) {
            System.out.println("Delete Successfully!");
        } else {
            System.out.println("No delete!");
        }
    }

    public static void selectShopByStaffInterface(Staff user) {
        List<Shop> staShopList = selectShopByStaff(user, manage.getShopList());
        int totalLength = TOTALLENGTH;
        topInterface(totalLength);
        String title = "Shops Information";
        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow(title, totalLength, "centre"));
        for (Shop shop : staShopList) {
            System.out.println(loopAdd("=", totalLength));
            System.out.println(formatOneRow("shop number: " + shop.getShopId(), totalLength, "left"));
            System.out.println(formatOneRow("shop name:" + shop.getName(), totalLength, "left"));
            System.out.println(formatOneRow("shop address: " + shop.getStreet() + ", " + shop.getCity() + ", " + shop.getState(), totalLength, "left"));
            System.out.println(formatOneRow("opening date: " + ((null == shop.getOpenDate()) ? "" : getDate(shop.getOpenDate())), totalLength, "left"));
            System.out.println(formatOneRow("owner: Oliver", totalLength, "left"));
            List<Staff> manageList = selectManagerByShopId(shop, manage.getStaffList());
            String managers = "";
            for (int i = 0; i < manageList.size(); i++) {
                managers += (i == (manageList.size() - 1)) ? manageList.get(i).getName() : manageList.get(i).getName() + ", ";
            }
            System.out.println(formatOneRow("manager: " + managers, totalLength, "left"));
            System.out.println(formatOneRow("staff number: " + shop.getEmplyeeNumber(), totalLength, "left"));
            System.out.println(formatOneRow("reservation call: " + shop.getPhone(), totalLength, "left"));
            System.out.println(loopAdd("=", totalLength));
        }
    }

    public static void createStaffInterface(Staff user) {
        Scanner scanner = new Scanner(System.in);
        Staff staff = new Staff();
        int totalLength = TOTALLENGTH;
        topInterface(totalLength);

        System.out.println("Please input the name of staff: ");
        String name = scanner.nextLine().trim();
        while (!stringLetter(name) || name.equals("")) {
            System.out.println("The name should be Alphabet: ");
            name = scanner.nextLine().trim();
        }
        System.out.println("Please input the email of staff: ");
        String email = scanner.nextLine().trim();
        while (!Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$").matcher(email).matches()){
                  System.out.println("Please input 'xxx@xxx.xxx' ");
                  email = scanner.nextLine().trim();
        }
        System.out.println("Please input the password of staff: ");
        String password = scanner.nextLine().trim();
        while (password.equals("")) {
            System.out.println("Password can not be empty: ");
            password = scanner.nextLine().trim();
        }
        String type = "";
        while (true) {
            System.out.println("Please input the type of staff: ");
            type = scanner.nextLine().trim();
            if ("manager".equals(type.toLowerCase()) || "staff".equals(type.toLowerCase()) || "owner".equals(type.toLowerCase())) {
                break;
            } else {
                System.out.println("The type must be Manager, Staff, or Owner");
            }
        }
        System.out.println("Please input the street of staff: ");
        String street = scanner.nextLine().trim();
        while (!stringLetter(street) && street.equals("")) {
            System.out.println("Please re-input street name(Letter only): ");
            street = scanner.nextLine().trim();
        }
        System.out.println("Please input the city of staff: ");
        String city = scanner.nextLine().trim();
        while (!stringLetter(city) && city.equals("")) {
            System.out.println("Please re-input city name(Letter only): ");
            city = scanner.nextLine().trim();
        }
        System.out.println("Please input the state of staff: ");
        String state = scanner.nextLine().trim();
        while (!stringLetter(state) && state.equals("")) {
            System.out.println("Please re-input state name(Letter only): ");
            state = scanner.nextLine().trim();
        }
        System.out.println("Please input the phone of staff: ");
        String phone = scanner.nextLine().trim();
        while (!Pattern.compile("\\(0[1-9]\\)[0-9]{8}").matcher(phone).matches() || phone.equals("")) {
            System.out.println("Please only input with this format '(0x)xxxxxxxx': ");
            phone = scanner.nextLine().trim();
        }
        String postal = "";
        while (true) {
            System.out.println("Please input the postal (xxxx) of staff: ");
            postal = scanner.nextLine().trim();
            if (stringIsInteger(postal) && !postal.equals("") && postal.length() <= 4) {
                break;
            } else {
                System.out.println("The postal is invalid!");
            }
        }

        List<Shop> shopStaffList = new ArrayList<Shop>();
        int indicator = 0;
        while (indicator == 0) {
            System.out.println("Please input the shops that this staff belongs to: (if more than one, divide shops by ',')");
            String shopBelong = scanner.nextLine().trim();
            if (shopBelong.equals("")){
                System.out.println("Shop id can not be empty");
            }
            String[] shopArr;
            if (shopBelong.contains(",")){
                shopArr = shopBelong.split(",");
            }
            else{
                shopArr = new String[]{shopBelong};
            }
            for (String shopA : shopArr) {
                if (!stringIsInteger(shopA)) {
                    System.out.println("Please input Integer as shop ID");
                    break;
                }else{

                        for (Shop shops : manage.getShopList()) {
                            if (shops.getShopId() == Integer.parseInt(shopA)) {
                                shopStaffList.add(shops);
                                indicator = 1;
                            }
                        }

                }
            }
        }

        staff.setName(name);
        staff.setCity(city);
        staff.setEmail(email);
        staff.setPassword(password);
        staff.setPhone(phone);
        staff.setPostal(Integer.parseInt(postal));
        staff.setState(state);
        staff.setShoplist(shopStaffList);
        staff.setStreet(street);
        staff.setStaffId(manage.getStaffList().get(manage.getStaffList().size() - 1).getStaffId() + 1);
        staff.setType(type);
        staff.setStatus("in");
        staff.setTFN(Long.parseLong(manage.getStaffList().get(manage.getStaffList().size() - 1).getTFN()) + 1 + "");

        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Staff number: " + staff.getStaffId(), totalLength, "left"));
        System.out.println(formatOneRow("Name: " + staff.getName(), totalLength, "left"));
        System.out.println(formatOneRow("Email: " + staff.getEmail(), totalLength, "left"));
        System.out.println(formatOneRow("Password: " + staff.getPassword(), totalLength, "left"));
        System.out.println(formatOneRow("Staff type: " + staff.getType(), totalLength, "left"));
        System.out.println(formatOneRow("TFN: " + staff.getTFN(), totalLength, "left"));
        System.out.println(formatOneRow("Street: " + staff.getStreet(), totalLength, "left"));
        System.out.println(formatOneRow("City: " + staff.getCity(), totalLength, "left"));
        System.out.println(formatOneRow("State: " + staff.getState(), totalLength, "left"));
        System.out.println(formatOneRow("Postal: " + staff.getPostal(), totalLength, "left"));
        System.out.println(formatOneRow("Contact number: " + staff.getPhone(), totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));
        createStaff(staff);
        List<Staff> originalList = manage.getStaffList();
        originalList.add(staff);
        manage.setStaffList(originalList);
    }

    public static void createItemInterface(Shop shop) {
        Scanner scanner = new Scanner(System.in);
        Item item = new Item();
        int totalLength = TOTALLENGTH;
        topInterface(totalLength);
        boolean exist = false;
        String name = "";

        while (true) {
            System.out.println("Please input the name of item: ");
            name = scanner.nextLine().trim();
            if (stringLetter(name)) {
                if (null != selectItemByName(name)) {
                    exist = true;
                    item = selectItemByName(name);
                    if (!item.getShopList().contains(shop)) {
                        item.getShopList().add(shop);
                        if (updateInventoryByItem(item)) {
                            System.out.println("The item: " + item.getName() + " is available in this shop now!");
                            break;
                        } else {
                            System.out.println("Update error!");
                        }
                    } else {
                        System.out.println("This item is available in this shop!");
                        break;
                    }
                } else {
                    break;
                }
            } else {
                System.out.println("The name of item should be letters!");
            }
        }

        if (!exist) {
            String priceString = "";
            while (true) {
                System.out.println("Please input the price of item: ");
                priceString = scanner.nextLine().trim();
                try {
                    int price = Integer.parseInt(priceString);
                    break;
                } catch (Exception e) {
                    System.out.println("The price of item should be number(Integer)!");
                }

            }
            String stockString = "";
            while (true) {
                System.out.println("Please input the stock of item: ");
                stockString = scanner.nextLine().trim();
                try {
                    int stock = Integer.parseInt(stockString);
                    break;
                } catch (Exception e) {
                    System.out.println("The stock of item should be number (Integer)!");
                }

            }

            String type = "";
            while (true) {
                System.out.println("Please input the type of item: ");
                type = scanner.nextLine().trim();
                if ("food".equals(type.toLowerCase()) || "coffee".equals(type.toLowerCase())) {
                    break;
                } else {
                    System.out.println("The type must be food or coffee.");
                }
            }

            List<Shop> shopList = new ArrayList<Shop>();
            shopList.add(shop);
            item.setName(name);
            item.setPrice(Double.parseDouble(priceString));
            item.setStock(Integer.parseInt(stockString));
            item.setType(type);
            item.setShopList(shopList);
            item.setAddDate(new Date());
            item.setItemId("I" + "-" + (Integer.parseInt(manage.getItemList().get(manage.getItemList().size() - 1).getItemId().split("-")[1]) + 1));
        }

        String shops = "";
        for (int i = 0; i < item.getShopList().size(); i++) {
            shops += i == item.getShopList().size() - 1 ? item.getShopList().get(i).getShopId() : item.getShopList().get(i).getShopId() + ", ";
        }
        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Item ID: " + item.getItemId(), totalLength, "left"));
        System.out.println(formatOneRow("Name: " + item.getName(), totalLength, "left"));
        System.out.println(formatOneRow("Price: " + item.getPrice(), totalLength, "left"));
        System.out.println(formatOneRow("Stock: " + item.getStock(), totalLength, "left"));
        System.out.println(formatOneRow("item type: " + item.getType(), totalLength, "left"));
        System.out.println(formatOneRow("Shop: " + shops, totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));
        manage.getItemList().add(item);
        if (createItem(item)) {
            System.out.println("Add new item successfully!");
        } else {
            System.out.println("Add new item failed!");
        }
    }

    public static void showOrderInterface(Shop shop) {
        //table
        int totalLength = TOTALLENGTH;
        List<Order> orderList = showAllOrdersByShopId(shop.getShopId());
        for (int i = 0; i < orderList.size(); i++) {
            List<Integer> stringLengthList = new ArrayList<Integer>();
            List<List> dataList = new ArrayList<List>();
            List<Item> itemList = orderList.get(i).getItem();
            List<Integer> quantityList = orderList.get(i).getQuantity();
            List<Integer> priceList = orderList.get(i).getPrice();

            stringLengthList.add("order".length());
            String orderNum = "order number: " + (orderList.get(i).getOrderId());
            stringLengthList.add(orderNum.length());
            List<String> titleList = new ArrayList<String>();
            titleList.add("name of the item");
            titleList.add("total cost of the item");
            titleList.add("quantity");
            dataList.add(titleList);
            String orderCreate = "order created by: " + user.getName();
            stringLengthList.add(orderCreate.length());
            String orderDate = "order date: " + getDate(orderList.get(i).getDate());
            stringLengthList.add(orderDate.length());
            String orderTime = "order time: " + getTime(orderList.get(i).getDate());
            stringLengthList.add(orderTime.length());
            String orderStatus = "order status: " + orderList.get(i).getStatus();
            stringLengthList.add(orderStatus.length());
            String custName = "customer name: " + orderList.get(i).getCustomerName();
            stringLengthList.add(custName.length());
            String phone = "phone Num: " + (null == orderList.get(i).getCustomerPhone() ? "" : orderList.get(i).getCustomerPhone());
            stringLengthList.add(phone.length());
            String orderType = "order type: " + orderList.get(i).getType();
            stringLengthList.add(orderType.length());

            if (null != itemList.get(0).getItemId()) {
                for (int b = 0; b < itemList.size(); b++) {
                    List<String> rowList = new ArrayList<String>();
                    rowList.add(itemList.get(b).getName());
                    rowList.add(Integer.toString(priceList.get(b)));
                    rowList.add(Integer.toString(quantityList.get(b)));
                    dataList.add(rowList);
                }
            } else {
                List<String> rowList = new ArrayList<String>();
                rowList.add("");
                rowList.add("");
                rowList.add("");
                dataList.add(rowList);
            }
            String cost = "total cost: " + orderList.get(i).getTotalCost();
            stringLengthList.add(cost.length());
            List<Integer> dataLengthList = getEachCellLengthTable(dataList, titleList.size());
            int dataLength = 0;
            for (int a : dataLengthList) {
                dataLength += a;
            }

            stringLengthList.add(dataLength);
            totalLength = Collections.max(stringLengthList) + 2 + titleList.size() - 1;

            System.out.println(loopAdd("=", totalLength));
            System.out.println(formatOneRow("order", totalLength, "centre"));
            System.out.println(formatOneRow(orderNum, totalLength, "left"));
            System.out.println(formatOneTable(dataList, totalLength, titleList.size()));
            System.out.println(formatOneRow(orderCreate, totalLength, "left"));
            System.out.println(formatOneRow(orderDate, totalLength, "left"));
            System.out.println(formatOneRow(orderTime, totalLength, "left"));
            System.out.println(formatOneRow(orderStatus, totalLength, "left"));
            System.out.println(formatOneRow(custName, totalLength, "left"));
            System.out.println(formatOneRow(phone, totalLength, "left"));
            System.out.println(formatOneRow(orderType, totalLength, "left"));
            System.out.println(formatOneRow(cost, totalLength, "left"));
            System.out.println(loopAdd("=", totalLength));
        }
    }

    public static void showAchievedOrderInterface(Shop shop) {
        //table
        int totalLength = TOTALLENGTH;
        List<Order> orderList = showAllOrdersByShopId(shop.getShopId());
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getStatus().toLowerCase().equals("achieved")) {
                List<Integer> stringLengthList = new ArrayList<Integer>();
                List<List> dataList = new ArrayList<List>();
                List<Item> itemList = orderList.get(i).getItem();
                List<Integer> quantityList = orderList.get(i).getQuantity();
                List<Integer> priceList = orderList.get(i).getPrice();

                stringLengthList.add("order".length());
                String orderNum = "order number: " + (orderList.get(i).getOrderId());
                stringLengthList.add(orderNum.length());
                List<String> titleList = new ArrayList<String>();
                titleList.add("name of the item");
                titleList.add("total cost of the item");
                titleList.add("quantity");
                dataList.add(titleList);
                String orderCreate = "order created by: " + user.getName();
                stringLengthList.add(orderCreate.length());
                String orderDate = "order date: " + getDate(orderList.get(i).getDate());
                stringLengthList.add(orderDate.length());
                String orderTime = "order time: " + getTime(orderList.get(i).getDate());
                stringLengthList.add(orderTime.length());
                String orderStatus = "order status: " + orderList.get(i).getStatus();
                stringLengthList.add(orderStatus.length());
                String custName = "customer name: " + orderList.get(i).getCustomerName();
                stringLengthList.add(custName.length());
                String phone = "phone Num: " + (null == orderList.get(i).getCustomerPhone() ? "" : orderList.get(i).getCustomerPhone());
                stringLengthList.add(phone.length());
                String orderType = "order type: " + orderList.get(i).getType();
                stringLengthList.add(orderType.length());


                for (int b = 0; b < itemList.size(); b++) {
                    List<String> rowList = new ArrayList<String>();
                    rowList.add(itemList.get(b).getName());
                    rowList.add(Integer.toString(priceList.get(b)));
                    rowList.add(Integer.toString(quantityList.get(b)));

                    dataList.add(rowList);
                }
                String cost = "total cost: " + orderList.get(i).getTotalCost();
                stringLengthList.add(cost.length());
                List<Integer> dataLengthList = getEachCellLengthTable(dataList, titleList.size());
                int dataLength = 0;
                for (int a : dataLengthList) {
                    dataLength += a;
                }

                stringLengthList.add(dataLength);
                totalLength = Collections.max(stringLengthList) + 2 + titleList.size() - 1;

                System.out.println(loopAdd("=", totalLength));
                System.out.println(formatOneRow("order", totalLength, "centre"));
                System.out.println(formatOneRow(orderNum, totalLength, "left"));
                System.out.println(formatOneTable(dataList, totalLength, titleList.size()));
                System.out.println(formatOneRow(orderCreate, totalLength, "left"));
                System.out.println(formatOneRow(orderDate, totalLength, "left"));
                System.out.println(formatOneRow(orderTime, totalLength, "left"));
                System.out.println(formatOneRow(orderStatus, totalLength, "left"));
                System.out.println(formatOneRow(custName, totalLength, "left"));
                System.out.println(formatOneRow(phone, totalLength, "left"));
                System.out.println(formatOneRow(orderType, totalLength, "left"));
                System.out.println(formatOneRow(cost, totalLength, "left"));
                System.out.println(loopAdd("=", totalLength));
            }
        }
    }

    public static void deleteOrderByIdInterface() {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();
        List<Order> shopOrderList = selectOrderByShop(shop, manage.getOrderList());
        while (true) {
            System.out.println("Please input the ID(integer) of the order you want to delete: ");
            String orderId = scanner.next();
            if (stringIsInteger(orderId)) {
                order = selectOrderByOrderId(Integer.parseInt(orderId), shopOrderList);
                if (null != order) {
                    break;
                } else {
                    System.out.println("This order does not exist in this shop!");
                }
            } else {
                System.out.println("The ID of order must be integer!");
            }
        }
        List<Integer> stringLengthList = new ArrayList<Integer>();
        List<List> dataList = new ArrayList<List>();
        List<Item> itemList = order.getItem();
        List<Integer> quantityList = order.getQuantity();
        List<Integer> priceList = order.getPrice();
        stringLengthList.add("order".length());
        String orderNum = "order number: " + (order.getOrderId());
        stringLengthList.add(orderNum.length());
        List<String> titleList = new ArrayList<String>();
        titleList.add("name of the item");
        titleList.add("total cost of the item");
        titleList.add("quantity");
        dataList.add(titleList);
        String orderCreate = "order created by: " + user.getName();
        stringLengthList.add(orderCreate.length());
        String orderDate = "order date: " + getDate(order.getDate());
        stringLengthList.add(orderDate.length());
        String orderTime = "order time: " + getTime(order.getDate());
        stringLengthList.add(orderTime.length());
        String orderStatus = "order status: " + order.getStatus();
        stringLengthList.add(orderStatus.length());
        String custName = "customer name: " + order.getCustomerName();
        stringLengthList.add(custName.length());
        String phone = "phone Num: " + order.getCustomerPhone();
        stringLengthList.add(phone.length());
        String orderType = "order type: " + order.getType();
        stringLengthList.add(orderType.length());

        if (null != itemList.get(0).getItemId()) {
            for (int b = 0; b < itemList.size(); b++) {
                List<String> rowList = new ArrayList<String>();
                rowList.add(itemList.get(b).getName());
                rowList.add(Integer.toString(priceList.get(b)));
                rowList.add(Integer.toString(quantityList.get(b)));
                dataList.add(rowList);
            }
        } else {
            List<String> rowList = new ArrayList<String>();
            rowList.add("");
            rowList.add("");
            rowList.add("");
            dataList.add(rowList);
        }
        String cost = "total cost: " + order.getTotalCost();
        stringLengthList.add(cost.length());
        List<Integer> dataLengthList = getEachCellLengthTable(dataList, titleList.size());
        int dataLength = 0;
        for (int a : dataLengthList) {
            dataLength += a;
        }

        stringLengthList.add(dataLength);
        totalLength = Collections.max(stringLengthList) + 2 + titleList.size() - 1;

        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("order", totalLength, "centre"));
        System.out.println(formatOneRow(orderNum, totalLength, "left"));
        System.out.println(formatOneTable(dataList, totalLength, titleList.size()));
        System.out.println(formatOneRow(orderCreate, totalLength, "left"));
        System.out.println(formatOneRow(orderDate, totalLength, "left"));
        System.out.println(formatOneRow(orderTime, totalLength, "left"));
        System.out.println(formatOneRow(orderStatus, totalLength, "left"));
        System.out.println(formatOneRow(custName, totalLength, "left"));
        System.out.println(formatOneRow(phone, totalLength, "left"));
        System.out.println(formatOneRow(orderType, totalLength, "left"));
        System.out.println(formatOneRow(cost, totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));

        while (true) {
            System.out.println("Please input 'submit' to confirm deleting ('back to back'): ");
            String option = scanner.next();
            if ("submit".equals(option)) {
                boolean suss = deleteOrderById(order);
                if (suss) {
                    System.out.println("Delete Successfully!");
                } else {
                    System.out.println("Delete Unsuccessfully!");
                }
                break;
            } else if ("back".equals(option)) {
                break;
            } else {
                System.out.println("You can only input 'submit' or 'back'!");
            }
        }
    }

    public static void checkOrderByOrderId() {
        int totalLength = TOTALLENGTH;
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();
        while (true) {
            System.out.println("Please input the ID(integer) of the order you want to check: ");
            String orderId = scanner.next();
            if (stringIsInteger(orderId) && orderId.length() <= 4) {
                order = selectOrderByOrderId(Integer.parseInt(orderId), manage.getOrderList());
                if (null != order) {
                    break;
                } else {
                    System.out.println("This order does not exist!");
                }
            } else {
                System.out.println("The input is invalid!");
            }
        }
        List<Integer> stringLengthList = new ArrayList<Integer>();
        List<List> dataList = new ArrayList<List>();
        List<Item> itemList = order.getItem();
        List<Integer> quantityList = order.getQuantity();
        List<Integer> priceList = order.getPrice();
        stringLengthList.add("order".length());
        String orderNum = "order number: " + (order.getOrderId());
        stringLengthList.add(orderNum.length());
        List<String> titleList = new ArrayList<String>();
        titleList.add("name of the item");
        titleList.add("total cost of the item");
        titleList.add("quantity");
        dataList.add(titleList);
        String orderCreate = "order created by: " + user.getName();
        stringLengthList.add(orderCreate.length());
        String orderDate = "order date: " + getDate(order.getDate());
        stringLengthList.add(orderDate.length());
        String orderTime = "order time: " + getTime(order.getDate());
        stringLengthList.add(orderTime.length());
        String orderStatus = "order status: " + order.getStatus();
        stringLengthList.add(orderStatus.length());
        String custName = "customer name: " + order.getCustomerName();
        stringLengthList.add(custName.length());
        String phone = "phone Num: " + order.getCustomerPhone();
        stringLengthList.add(phone.length());
        String orderType = "order type: " + order.getType();
        stringLengthList.add(orderType.length());


        for (int b = 0; b < itemList.size(); b++) {
            List<String> rowList = new ArrayList<String>();
            rowList.add(itemList.get(b).getName());
            rowList.add(Integer.toString(priceList.get(b)));
            rowList.add(Integer.toString(quantityList.get(b)));

            dataList.add(rowList);
        }
        String cost = "total cost: " + order.getTotalCost();
        stringLengthList.add(cost.length());
        List<Integer> dataLengthList = getEachCellLengthTable(dataList, titleList.size());
        int dataLength = 0;
        for (int a : dataLengthList) {
            dataLength += a;
        }

        stringLengthList.add(dataLength);
        totalLength = Collections.max(stringLengthList) + 2 + titleList.size() - 1;

        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("order", totalLength, "centre"));
        System.out.println(formatOneRow(orderNum, totalLength, "left"));
        System.out.println(formatOneTable(dataList, totalLength, titleList.size()));
        System.out.println(formatOneRow(orderCreate, totalLength, "left"));
        System.out.println(formatOneRow(orderDate, totalLength, "left"));
        System.out.println(formatOneRow(orderTime, totalLength, "left"));
        System.out.println(formatOneRow(orderStatus, totalLength, "left"));
        System.out.println(formatOneRow(custName, totalLength, "left"));
        System.out.println(formatOneRow(phone, totalLength, "left"));
        System.out.println(formatOneRow(orderType, totalLength, "left"));
        System.out.println(formatOneRow(cost, totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));


    }

    public static void showRevenueReportOneShop(Shop shop, Date date) {
        int totalLength = TOTALLENGTH;
        topInterface(totalLength);

        RevenueReport report = generateRevenueReportOneShop(shop.getShopId(), date);

        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Monthly Statement ", totalLength, "centre"));
        System.out.println(formatOneRow("Revenue: " + report.getRevenue() + "$", totalLength, "left"));
        System.out.println(formatOneRow("Total Sold Coffee: " + report.getTotalSellCoffee(), totalLength, "left"));
        System.out.println(formatOneRow("Total Sold Bean: " + report.getTotalSellBean(), totalLength, "left"));
        System.out.println(formatOneRow("Total Sold food: " + report.getTotalSellFood(), totalLength, "left"));
        System.out.println(formatOneRow("Best Sold Coffee: " + report.getBestSellItem(), totalLength, "left"));
        System.out.println(formatOneRow("Best Sell Day Week1: " + report.getBestSellDay().get(3), totalLength, "left"));
        System.out.println(formatOneRow("Best Sell Day Week2: " + report.getBestSellDay().get(2), totalLength, "left"));
        System.out.println(formatOneRow("Best Sell Day Week3: " + report.getBestSellDay().get(1), totalLength, "left"));
        System.out.println(formatOneRow("Best Sell Day Week4: " + report.getBestSellDay().get(0), totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));
    }

    public static void showBusinessReportOneShop(Shop shop, Date date, Date addDate) {
        int totalLength = TOTALLENGTH;
        topInterface(totalLength);

        BusinessReport report = generateBusinessReportOneShop(shop, date, addDate);
        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("Weekly Statement ", totalLength, "centre"));
        System.out.println(formatOneRow("Revenue: " + report.getRevenue() + "$", totalLength, "left"));
        for (int i = 0; i < report.getItemLowInventory().size(); i++) {
            System.out.println(formatOneRow("Low Inventory: " + report.getItemLowInventory().get(i).getName(), totalLength, "left"));
        }
        System.out.println(formatOneRow("Most Sale Day: " + report.getDayMostIncome(), totalLength, "left"));
        System.out.println(loopAdd("=", totalLength));
    }

    public static boolean logout() {
        System.out.println("Please input \"logout\" to logout");
        Scanner scanner = new Scanner(System.in);
        String logout = scanner.next();
        if (logout.equals("logout")) {
            return false;
        }
        else{
            System.out.println("Please only input 'logout'");
        }
        return true;
    }

    public static void topInterface(int totalLength) {
        System.out.println();
        System.out.println(loopAdd("=", totalLength));
        System.out.println(formatOneRow("bake shop system", totalLength, "centre"));
        System.out.println(loopAdd("=", totalLength));
    }

    public static Staff getUser() {
        return user;
    }

    public static void setUser(Staff user) {
        BakeShop.user = user;
    }

    public static Shop getShop() {
        return shop;
    }

    public static void setShop(Shop shop) {
        BakeShop.shop = shop;
    }

    public static Manage getManage() {
        return manage;
    }

    public static void setManage(Manage manage) {
        BakeShop.manage = manage;
    }
}
