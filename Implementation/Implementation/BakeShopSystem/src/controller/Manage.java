package controller;

import entity.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;
import static util.ProcessDate.*;
import static util.VerifyString.convertCellValueToString;

public class Manage {
    private static File file;
    private static List<Shop> shopList;
    private static List<Staff> staffList;
    private static List<Item> itemList;
    private static List<Order> orderList;

    public Manage() {
        file = new File("BakeShopSystem/src/data/Sample Data.xlsx");
        shopList = readShopDetails(file);
        itemList = readItemDetails(file,shopList);
        staffList = readStaffDetails(file, shopList);
        orderList = readOrderDetails(file, staffList, shopList);
    };

    public static List<Staff> readStaffDetails(File file, List<Shop> shopList) {
        InputStream is = null;
        List<Staff> staffList = new ArrayList<Staff>();
        Staff staff = null;
        Sheet sheet = null;
        try {
            is = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(is);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if ("users".equals(workbook.getSheetName(i))) {
                    sheet = workbook.getSheetAt(i); // the first sheet: staff
                }
            }

            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                System.out.println("Excel has no data");
            }

            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                staff = new Staff();

                if (null == row) {
                    continue;
                }

                Cell cell;
                int cellNum = 0;

                // staffId
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setStaffId((int) Double.parseDouble(convertCellValueToString(cell).trim()));
                }

                // name
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setName(convertCellValueToString(cell).trim());
                }

                // email
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setEmail(convertCellValueToString(cell).trim());
                }

                // password
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setPassword(convertCellValueToString(cell).trim());
                }

                // type
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setType(convertCellValueToString(cell).trim());
                }

                // TFN?
                cell = row.getCell(cellNum++);
                DecimalFormat df = new DecimalFormat("#");
                if (null != convertCellValueToString(cell)) {
                    staff.setTFN(df.format(Double.parseDouble(convertCellValueToString(cell).trim())));
                }

                // street
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setStreet(convertCellValueToString(cell).trim());
                }

                // city
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setCity(convertCellValueToString(cell).trim());
                }

                // state
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setState(convertCellValueToString(cell).trim());
                }

                // postal
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setPostal((int) Double.parseDouble(convertCellValueToString(cell).trim()));
                }

                // phone
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    staff.setPhone(convertCellValueToString(cell).trim());
                }

                // status
                staff.setStatus("in");

                // shop
                cell = row.getCell(cellNum++);
                List<Shop> shopSelectedList = new ArrayList<Shop>();
                String shopIdList = convertCellValueToString(cell).trim();
                if (null != shopIdList) {
                    if (shopIdList.contains("|")) {
                        String shopIdArray[] = shopIdList.split("\\|");
                        for (String shopId : shopIdArray) {
                            shopSelectedList.add(selectShopByShopId(Integer.parseInt(shopId), shopList));
                        }
                    } else {
                        shopSelectedList.add(selectShopByShopId((int)Double.parseDouble(shopIdList), shopList));
                    }
                    staff.setShoplist(shopSelectedList);
                }

                staffList.add(staff);
            }
            for (Shop shop : shopList) {
                shop.setEmplyeeNumber(selectStaffByShop(shop, staffList).size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return staffList;
    }

    public static List<Shop> readShopDetails(File file){
        InputStream is = null;
        List<Shop> shopList = new ArrayList<Shop>();
        Shop shop = null;
        try {
            is = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(1); // the first sheet: staff

            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                System.out.println("Excel has no data");
            }

            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getLastRowNum() + 1;
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                shop = new Shop();

                if (null == row) {
                    continue;
                }

                Cell cell;
                int cellNum = 0;

                // shopId
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    shop.setShopId((int)Double.parseDouble(convertCellValueToString(cell).trim()));
                }


                // name
                if (null != convertCellValueToString(cell)) {
                    shop.setName("No. " + cellNum + " Shop");
                }

                // street
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    shop.setStreet(convertCellValueToString(cell).trim());
                }

                // city
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    shop.setCity(convertCellValueToString(cell).trim());
                }

                // state
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    shop.setState(convertCellValueToString(cell).trim());
                }

                // postal
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    shop.setPostal((int)Double.parseDouble(convertCellValueToString(cell).trim()));
                }

                // phone
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    shop.setPhone(convertCellValueToString(cell).trim());
                }

                //date
                cell = row.getCell(cellNum++);
                String date = convertCellValueToString(cell).trim();
                Date date1 = null;
                date1 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                if (null != date1) {
                    shop.setOpenDate(date1);
                }

                shopList.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return shopList;
    }

    public static List<Order> readOrderDetails(File file, List<Staff> staffList, List<Shop> shopList) {
        InputStream is = null;
        List<Order> orderList = new ArrayList<Order>();
        Order order = null;
        try {
            is = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(2); // the first sheet: staff

            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                System.out.println("Excel has no data");
            }

            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getLastRowNum() + 1;
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                order = new Order();

                if (null == row) {
                    continue;
                }

                Cell cell;
                int cellNum = 0;

                // shop
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    order.setShop(selectShopByShopId((int) Double.parseDouble(convertCellValueToString(cell).trim()), shopList));
                }

                // orderId
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    order.setOrderId((int) Double.parseDouble(convertCellValueToString(cell).trim()));
                }

                // staff
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    order.setStaff(selectStaffByStaffId((int) Double.parseDouble(convertCellValueToString(cell).trim()), staffList));
                }

                // item
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    String itemName = convertCellValueToString(cell).trim();
                    ArrayList<Item> newItemList = new ArrayList<Item>();
                    for (String oneItemName : itemName.split(",")) {
                        newItemList.add(selectItemByName(oneItemName.trim()));
                    }
                    order.setItem(newItemList);
                }

                // quantity
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell) && !"".equals(convertCellValueToString(cell))) {
                    String quantity = convertCellValueToString(cell).trim();
                    String quantityStringArray[] = quantity.split(",");
                    ArrayList<Integer> quantityList = new ArrayList<Integer>();
                    for (String qua : quantityStringArray) {
                        quantityList.add((int)Double.parseDouble(qua.trim()));
                    }
                    order.setQuantity(quantityList);
                }

                // price
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell) && !"".equals(convertCellValueToString(cell))) {
                    String price = convertCellValueToString(cell).trim();
                    String priceStringArray[] = price.split("\\|");
                    ArrayList<Integer> priceList = new ArrayList<Integer>();
                    for (String qua : priceStringArray) {
                        priceList.add((int)Double.parseDouble(qua));
                    }
                    order.setPrice(priceList);
                }

                // totalCost
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    order.setTotalCost(Double.parseDouble(convertCellValueToString(cell).trim()));
                }

                // date and time
                cell = row.getCell(cellNum++);
                String date = convertCellValueToString(cell).trim();
                cell = row.getCell(cellNum++);
                String time = convertCellValueToString(cell).trim();
                Date combinedDate = combineDateAndTime(date, time);
                if (null != combinedDate) {
                    order.setDate(combinedDate);
                }

                // customerName
                cell = row.getCell(cellNum++);
                if (convertCellValueToString(cell) != null) {
                    order.setCustomerName(convertCellValueToString(cell).trim());
                }

                // status
                cell = row.getCell(cellNum++);
                if (convertCellValueToString(cell) != null) {
                    order.setStatus(convertCellValueToString(cell).trim());
                }

                // customerphone
                cell = row.getCell(cellNum++);
                if (convertCellValueToString(cell) != null) {
                    order.setCustomerPhone(convertCellValueToString(cell).trim());
                }

                // type
                cell = row.getCell(cellNum++);
                if (convertCellValueToString(cell) != null) {
                    order.setType(convertCellValueToString(cell).trim());
                }

                orderList.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return orderList;
    }

    public static List<Item> readItemDetails(File file, List<Shop> shopList){
        InputStream is = null;
        List<Item> itemList = new ArrayList<Item>();
        Item item = null;
        List<Item> itemList2 = readItemPriceAndType(file);
        try {
            is = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(3);

            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                System.out.println("Excel has no data");
            }

            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getLastRowNum() + 1;
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                item = new Item();

                if (null == row) {
                    continue;
                }

                Cell cell;
                int cellNum = 0;
                // shop

                cell = row.getCell(cellNum++);
                List<Shop> shopList1 = new ArrayList<Shop>();
                String shopIdList = convertCellValueToString(cell).trim();

                if (null != shopIdList) {
                    if (shopIdList.contains("|")) {
                        String shopIdArray[] = shopIdList.split("\\|");

                        for (int i = 0; i < shopIdArray.length; i++) {
                            shopList1.add(selectShopByShopId(Integer.parseInt(shopIdArray[i]), shopList));
                        }
                    } else {
                        shopList1.add(selectShopByShopId((int)Double.parseDouble(shopIdList), shopList));
                    }
                    item.setShopList(shopList1);
                }

                // Id
                cell = row.getCell(cellNum++);
//				boolean flag = true;
//				for (Item item1 : itemList) {
//					if (convertCellValueToString(cell).trim().equals(item1.getItemId())) {
//						List<Shop> shopList11 = item1.getShopList();
//						for (Shop s : shopList1) {
//							shopList11.add(s);
//						}
//						item1.setShopList(shopList11);
//						flag = false;
//					}
//				}
//				if (!flag){
//					continue;
//				}
//
//

                if (null != convertCellValueToString(cell)) {
                    item.setItemId(convertCellValueToString(cell).trim());
                }

                // name
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    item.setName(convertCellValueToString(cell).trim());
                }

                // Stock
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    item.setStock((int)(Double.parseDouble(convertCellValueToString(cell).trim())));
                }
                //date
                cell = row.getCell(cellNum++);
                String date = convertCellValueToString(cell).trim();
                Date date1 =new SimpleDateFormat("MM/dd/yyyy").parse(date);
                if (null != date1) {
                    item.setAddDate(date1);
                }

                for (Item item3 : itemList2){
                    if (item.getItemId().equals(item3.getItemId())){
                        item.setType(item3.getType());
                        item.setPrice(item3.getPrice());
                        break;
                    }
                }

                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return itemList;
    }

    public static List<Item> readItemPriceAndType(File file){
        InputStream is = null;
        List<Item> itemList = new ArrayList<Item>();
        Item item = null;
        try {
            is = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(4); // the first sheet: staff

            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                System.out.println("Excel has no data");
            }

            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getLastRowNum() + 1;
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                item = new Item();

                if (null == row) {
                    continue;
                }

                Cell cell;
                int cellNum = 0;

                // Id
                cell = row.getCell(cellNum++);
                boolean flag = true;
                for (Item item1 : itemList) {
                    if (convertCellValueToString(cell).trim().equals(item1.getItemId())) {
                        flag = false;
                    }
                }
                if (!flag){
                    continue;
                }
                if (null != convertCellValueToString(cell)) {
                    item.setItemId(convertCellValueToString(cell).trim());
                }

                // name
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    item.setName(convertCellValueToString(cell).trim());
                }

                // price
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    item.setPrice((int)(Double.parseDouble(convertCellValueToString(cell).trim())));
                }

                // type
                cell = row.getCell(cellNum++);
                if (null != convertCellValueToString(cell)) {
                    item.setType(convertCellValueToString(cell).trim());
                }


                itemList.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return itemList;
    }

    public static List<Order> showAllOrdersByShopId(int shopId) {
        Shop shop = new Shop();
        for(int i =0; i< shopList.size();i++){
            if(shopList.get(i).getShopId() == shopId){
                shop = shopList.get(i);
                break;
            }
        }
        List<Order> allOrderByShopId = selectOrderByShop(shop, orderList);
        return allOrderByShopId;
    };

    public static Item selectItemByName(String name){
        for (int i = 0; i < itemList.size(); i++) {
            if (name.toLowerCase().equals(itemList.get(i).getName().toLowerCase())){
                return itemList.get(i);
            }
        }
        return null;
    }

    public static List<Order> selectOrderByShop(Shop shop, List<Order> orderList){
        List<Order> orderSelectedList = new ArrayList<Order>();
        for (int i = 0; i < orderList.size(); i++) {
            if (shop.getShopId() == orderList.get(i).getShop().getShopId()){
                orderSelectedList.add(orderList.get(i));
            }
        }
        return orderSelectedList;
    }

    public static Order selectOrderByOrderId(int orderId, List<Order> orderList){
        for (int i = 0; i < orderList.size(); i++) {
            if (orderId == orderList.get(i).getOrderId()){
                return orderList.get(i);
            }
        }
        return null;
    }

    public static boolean updateInventoryByItem(Item item){
        boolean succ = true;
        try {
            FileInputStream in = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(3);
            XSSFRow nxrow = null;
            for (int i = 0; i < itemList.size(); i++) {
                if (item.getName().toLowerCase().equals(itemList.get(i).getName().toLowerCase())) {
                    nxrow = sheet.getRow(i + 1);
                }
            }
            FileOutputStream out = new FileOutputStream(file);

            String shops = "";
            for (int i = 0; i < item.getShopList().size(); i++){
                shops += i == item.getShopList().size() - 1 ? item.getShopList().get(i).getShopId() : item.getShopList().get(i).getShopId() + "|";
            }

            // column
            nxrow.createCell(0).setCellValue(shops);
            nxrow.createCell(1).setCellValue(item.getItemId());
            nxrow.createCell(2).setCellValue(item.getName());
            nxrow.createCell(3).setCellValue((int)item.getStock() + "");
            nxrow.createCell(4).setCellValue(getDate(item.getAddDate()));

            out.flush();
            workbook.write(out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return succ;
    }

    public static boolean createOrder(Order order){
        boolean succ = true;
        try {
            FileInputStream in = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(2);
            XSSFRow nxrow = sheet.createRow(orderList.size() + 1);

            FileOutputStream out = new FileOutputStream(file);

            String itemNames = "";
            for (int i = 0; i < order.getItem().size(); i++) {
                if (i != (order.getItem().size() - 1)){
                    itemNames += order.getItem().get(i).getName() + ", ";
                } else {
                    itemNames += order.getItem().get(i).getName();
                }
            }

            String quantitys = "";
            for (int i = 0; i < order.getItem().size(); i++) {
                if (i != (order.getQuantity().size() - 1)){
                    quantitys += order.getQuantity().get(i) + ", ";
                } else {
                    quantitys += order.getQuantity().get(i) + "";
                }
            }

            String prices = "";
            for (int i = 0; i < order.getPrice().size(); i++) {
                if (i != (order.getPrice().size() - 1)){
                    prices += order.getPrice().get(i) + "|";
                } else {
                    prices += order.getPrice().get(i) + "";
                }
            }

            // column
            nxrow.createCell(0).setCellValue(order.getShop().getShopId());
            nxrow.createCell(1).setCellValue(orderList.get(orderList.size() - 1).getOrderId() + 1);
            nxrow.createCell(2).setCellValue(order.getStaff().getStaffId());
            nxrow.createCell(3).setCellValue(itemNames);
            nxrow.createCell(4).setCellValue(quantitys);
            nxrow.createCell(5).setCellValue(prices);
            nxrow.createCell(6).setCellValue(order.getTotalCost());

            nxrow.createCell(7).setCellValue(getDate(order.getDate()));
            nxrow.createCell(8).setCellValue(getTime(order.getDate()));
            nxrow.createCell(9).setCellValue(order.getCustomerName());
            nxrow.createCell(10).setCellValue(order.getStatus());
            nxrow.createCell(11).setCellValue(order.getCustomerPhone());
            nxrow.createCell(12).setCellValue(order.getType());

            out.flush();
            workbook.write(out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return succ;
    }

    public static boolean createStaff(Staff staff){
        boolean succ = true;
        try {
            FileInputStream in = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow nxrow = sheet.createRow(staffList.size() + 1);

            FileOutputStream out = new FileOutputStream(file);

            String shopList = "";
            List<Shop> shopStaffList = staff.getShoplist();
            for (int i = 0; i < shopStaffList.size(); i++) {
                shopList += (i != (shopStaffList.size() - 1)) ? shopStaffList.get(i).getShopId() + "|" : shopStaffList.get(i).getShopId();
            }

            // column
            nxrow.createCell(0).setCellValue(staff.getStaffId());
            nxrow.createCell(1).setCellValue(staff.getName());
            nxrow.createCell(2).setCellValue(staff.getEmail());
            nxrow.createCell(3).setCellValue(staff.getPassword());
            nxrow.createCell(4).setCellValue(staff.getType());
            Cell cell5 = nxrow.createCell(5);
            cell5.setCellType(CellType.NUMERIC);
            cell5.setCellValue(staff.getTFN());
            nxrow.createCell(6).setCellValue(staff.getStreet());

            nxrow.createCell(7).setCellValue(staff.getCity());
            nxrow.createCell(8).setCellValue(staff.getState());
            nxrow.createCell(9).setCellValue(staff.getPostal());
            nxrow.createCell(10).setCellValue(staff.getPhone());
            nxrow.createCell(11).setCellValue(shopList);

            out.flush();
            workbook.write(out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return succ;
    }

    public static boolean createItem (Item item){
        boolean succ = true;
        try {
            FileInputStream in = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(3);
            XSSFRow nxrow = sheet.createRow(itemList.size());

            FileOutputStream out = new FileOutputStream(file);

            String shops = "";
            for (int i = 0; i < item.getShopList().size(); i++){
                shops += i == item.getShopList().size() - 1 ? item.getShopList().get(i).getShopId() : item.getShopList().get(i).getShopId() + "|";
            }
            // column
            nxrow.createCell(0).setCellValue(shops);
            nxrow.createCell(1).setCellValue(item.getItemId());
            nxrow.createCell(2).setCellValue(item.getName());
            nxrow.createCell(3).setCellValue((int)item.getStock() + "");
            nxrow.createCell(4).setCellValue(getDate(item.getAddDate()));

            out.flush();
            workbook.write(out);
            out.close();

            FileInputStream in2 = new FileInputStream(file);
            XSSFWorkbook workbook2 = new XSSFWorkbook(in2);
            XSSFSheet sheet2 = workbook2.getSheetAt(4);
            XSSFRow nxrow2 = sheet2.createRow(itemList.size());

            FileOutputStream out2 = new FileOutputStream(file);

            // column
            nxrow2.createCell(0).setCellValue(item.getItemId());
            nxrow2.createCell(1).setCellValue(item.getName());
            nxrow2.createCell(2).setCellValue((int)item.getPrice() + "");
            nxrow2.createCell(3).setCellValue(item.getType());

            out2.flush();
            workbook2.write(out2);
            out2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return succ;
    }

    public static boolean updateStaff(Staff staff){
        int rowNumber = 0;
        FileInputStream in = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        XSSFRow row = null;
        for (int i = 0; i < staffList.size(); i++){
            if (staffList.get(i).getStaffId() == staff.getStaffId()){
                rowNumber = i;
                break;
            }
        }
        try {
            in = new FileInputStream(file);
            workbook = new XSSFWorkbook(in);
            sheet = workbook.getSheetAt(0);
            row = sheet.getRow(rowNumber + 1);

            FileOutputStream out = new FileOutputStream(file);

            String shopList = "";
            List<Shop> shopStaffList = staff.getShoplist();
            for (int i = 0; i < shopStaffList.size(); i++) {
                shopList += (i != (shopStaffList.size() - 1)) ? shopStaffList.get(i).getShopId() + "|" : shopStaffList.get(i).getShopId();
            }

            row.createCell(0).setCellValue(staff.getStaffId());
            row.createCell(1).setCellValue(staff.getName());
            row.createCell(2).setCellValue(staff.getEmail());
            row.createCell(3).setCellValue(staff.getPassword());
            row.createCell(4).setCellValue(staff.getType());
            Cell cell5 = row.createCell(5);
            cell5.setCellType(CellType.NUMERIC);
            cell5.setCellValue(staff.getTFN());
            row.createCell(6).setCellValue(staff.getStreet());
            row.createCell(7).setCellValue(staff.getCity());
            row.createCell(8).setCellValue(staff.getState());
            row.createCell(9).setCellValue(staff.getPostal());
            row.createCell(10).setCellValue(staff.getPhone());
            row.createCell(11).setCellValue(shopList);

            out.flush();
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean updateOrder(Order order){
        int rowNumber = 0;
        FileInputStream in = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        XSSFRow row = null;
        for (int i = 0; i < orderList.size(); i++){
            if (orderList.get(i).getOrderId() == order.getOrderId()){
                rowNumber = i;
                break;
            }
        }
        try {
            in = new FileInputStream(file);
            workbook = new XSSFWorkbook(in);
            sheet = workbook.getSheetAt(2);
            row = sheet.getRow(rowNumber + 1);

            FileOutputStream out = new FileOutputStream(file);

            row.getCell(9).setCellValue(order.getCustomerName());
            row.getCell(10).setCellValue(order.getStatus());
            row.getCell(11).setCellValue(order.getCustomerPhone());

            out.flush();
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean deleteStaffById(Staff staff){
        FileInputStream in = null;
        Workbook workbook =null;
        XSSFSheet sheet = null;
        try {
            in = new FileInputStream(file);
            workbook = WorkbookFactory.create(in);
            sheet = (XSSFSheet) workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            int startRow = 0;
            for (int i = 0; i < staffList.size(); i++) {
                if (staffList.get(i).getStaffId() == staff.getStaffId()) {
                    startRow = i;
                }
            }

            if (startRow < lastRowNum - 1) {
                sheet.shiftRows(startRow + 2, lastRowNum, -1);
            } else {
                sheet.removeRow(sheet.getRow(startRow + 1));
            }

            staffList.remove(staff);

            if (sheet instanceof XSSFSheet) {
                XSSFSheet xSSFSheet = (XSSFSheet) sheet;
                for (int r = xSSFSheet.getFirstRowNum(); r < sheet.getLastRowNum() + 1; r++) {
                    XSSFRow row = xSSFSheet.getRow(r);
                    if (row != null) {
                        long rRef = row.getCTRow().getR();
                        for (Cell cell : row) {
                            String cRef = ((XSSFCell) cell).getCTCell().getR();
                            ((XSSFCell) cell).getCTCell().setR(cRef.replaceAll("[0-9]", "") + rRef);
                        }
                    }
                }
            }

            in.close();
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteOrderById(Order order){
        FileInputStream in = null;
        Workbook workbook =null;
        XSSFSheet sheet = null;
        try {
            in = new FileInputStream(file);
            workbook = WorkbookFactory.create(in);
            sheet = (XSSFSheet) workbook.getSheetAt(2);
            int lastRowNum = sheet.getLastRowNum();
            int startRow = 0;
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).getOrderId() == order.getOrderId()) {
                    startRow = i;
                    orderList.remove(i);
                }
            }
            if (startRow < lastRowNum - 1) {
                sheet.shiftRows(startRow + 2, lastRowNum, -1);
            } else {
                sheet.removeRow(sheet.getRow(startRow + 1));
            }
            if (sheet instanceof XSSFSheet) {
                XSSFSheet xSSFSheet = (XSSFSheet) sheet;
                for (int r = xSSFSheet.getFirstRowNum(); r < sheet.getLastRowNum() + 1; r++) {
                    XSSFRow row = xSSFSheet.getRow(r);
                    if (row != null) {
                        long rRef = row.getCTRow().getR();
                        for (Cell cell : row) {
                            String cRef = ((XSSFCell) cell).getCTCell().getR();
                            ((XSSFCell) cell).getCTCell().setR(cRef.replaceAll("[0-9]", "") + rRef);
                        }
                    }
                }
            }
            in.close();
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public static Shop selectShopByShopId(int shopId, List<Shop> shopList){
        for (int i = 0; i < shopList.size(); i++) {
            if (shopId == shopList.get(i).getShopId()){
                return shopList.get(i);
            }
        }
        return null;
    }

    public static Staff selectStaffByEmail(String email){
        for (int i = 0; i < staffList.size(); i++) {
            if (email.equals(staffList.get(i).getEmail())){
                return staffList.get(i);
            }
        }
        return null;
    }

    /**
     * select shop by staff (find the shops that the staff can access)
     */
    public static List<Shop> selectShopByStaff(Staff staff, List<Shop> shopList){
        List<Shop> shopSelectedList = new ArrayList<Shop>();
        for (int i = 0; i < shopList.size(); i++) {
            for (Shop shop : staff.getShoplist()) {
                if (shop.getShopId() == shopList.get(i).getShopId()){
                    shopSelectedList.add(shop);
                    break;
                }
            }
        }
        return shopSelectedList;
    }

    public static Staff selectStaffByStaffId(int staffId, List<Staff> staffList){
        for (int i = 0; i < staffList.size(); i++) {
            if (staffId == staffList.get(i).getStaffId()){
                return staffList.get(i);
            }
        }
        return null;
    }

    public static List<Staff> selectManagerByShopId(Shop shop, List<Staff> staffList){
        List<Staff> managerList = new ArrayList<Staff>();
        for (Staff staff : staffList){
            List<Shop> shopList = staff.getShoplist();
            if ("Manager".equals(staff.getType())){
                for (Shop staShop : shopList){
                    if (shop.getShopId() == staShop.getShopId()){
                        managerList.add(staff);
                    }
                }
            }
        }
        return managerList;
    }

    /**
     * select staff by shop (find the staffs that the shop has)
     */
    public static List<Staff> selectStaffByShop(Shop shop, List<Staff> staffList){
        List<Staff> staffSelectedList = new ArrayList<Staff>();
        for (int i = 0; i < staffList.size(); i++) {
            for (Shop staffShop : staffList.get(i).getShoplist()) {
                if (shop.getShopId() == staffShop.getShopId()) {
                    staffSelectedList.add(staffList.get(i));
                }
            }
        }
        return staffSelectedList;
    }

    public static RevenueReport generateRevenueReportOneShop(int shopId, Date oldDate){
        Date addDate = new Date();
        double revenue = 0;
        String bestSellItem = getMostSell(shopId, oldDate, "coffee");
        long totalSellFood = getTotalSell(shopId, oldDate, "food");
        long totalSellCoffee = getTotalSell(shopId, oldDate, "coffee");
        long totalSellBean = getTotalSell(shopId, oldDate, "bean");
        List<Order> shopOrder = showAllOrdersByShopId(shopId);
        List<Date> bestSellDay = new ArrayList<>();
        for (int i = 0; i < shopOrder.size(); i++) {
            if (shopOrder.get(i).getDate().getTime() > oldDate.getTime() && shopOrder.get(i).getDate().getTime() < addDate.getTime()) {
                revenue += shopOrder.get(i).getTotalCost();
            }
        }
        for (int i = -1; i > -5; i--){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_YEAR,i);
            Date pastDate = calendar.getTime();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.add(Calendar.WEEK_OF_YEAR,i+1);
            Date newDate = calendar2.getTime();
            BusinessReport bp = generateBusinessReportOneShop(selectShopByShopId(shopId, shopList),pastDate, newDate);
            bestSellDay.add(bp.getDayMostIncome());
        }
        RevenueReport rp = new RevenueReport(addDate, revenue, totalSellCoffee, totalSellBean, totalSellFood, bestSellItem, bestSellDay);
        return rp;
   }

    public static BusinessReport generateBusinessReportOneShop(Shop shop,Date oldDate, Date addDate){

       List<Item> restockList = new ArrayList<>();
       for(int i = 0; i < itemList.size();i++){
           List<Shop> shopList = itemList.get(i).getShopList();
           for(int a = 0; a< shopList.size();a++){
               if(shopList.get(a).getShopId() == shop.getShopId()){
                if(itemList.get(i).getStock()<=5){
                    restockList.add(itemList.get(i));
                    break;
                }
               }
           }
       }
        int revenue = 0;
        int bestSell = 0;
        Date bestDay =null;
        List<Order> shopOrder = showAllOrdersByShopId(shop.getShopId());
        int dailySel = 0;
        for (int i = 0; i < shopOrder.size();i++){
            if(shopOrder.get(i).getDate().getTime() > oldDate.getTime() && shopOrder.get(i).getDate().getTime() < addDate.getTime()) {
                dailySel += shopOrder.get(i).getTotalCost();
                if(getDate(shopOrder.get(i).getDate()) == (i + 1 == shopOrder.size() ? getDate(shopOrder.get(i).getDate()) :getDate(shopOrder.get(i+1).getDate())))
                {
                }else
                {
                    if(dailySel>bestSell){
                        bestSell = dailySel;
                        bestDay = shopOrder.get(i).getDate();
                    }
                    dailySel = 0;
                }
                revenue += shopOrder.get(i).getTotalCost();
            }
        }
        BusinessReport bp = new BusinessReport(bestDay,restockList,revenue);
       return bp;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setShopList(List<Shop> shopList) {
        Manage.shopList = shopList;
    }

    public void setStaffList(List<Staff> staffList) {
        Manage.staffList = staffList;
    }

    public void setItemList(List<Item> itemList) {
        Manage.itemList = itemList;
    }

    public void setOrderList(List<Order> orderList) {
        Manage.orderList = orderList;
    }

    public static String getMostSell(int shopId, Date oldDate, String type) {
        Date addDate = new Date();
        long bestSell = 0;
        String bestSellItem = "";
        List<Order> shopOrder = showAllOrdersByShopId(shopId);
        HashMap<String, Integer> sell = new HashMap<>();
        for (int i = 0; i < shopOrder.size(); i++) {
            if (shopOrder.get(i).getDate().getTime() > oldDate.getTime() && shopOrder.get(i).getDate().getTime() < addDate.getTime()) {
                List<Item> orderItemList = shopOrder.get(i).getItem();
                List<Integer> quantityList = shopOrder.get(i).getQuantity();
                for (int a = 0; a < quantityList.size(); a++) {
                    if (!sell.containsKey(orderItemList.get(a).getName()) && orderItemList.get(a).getType().equals(type)) {
                        sell.put(orderItemList.get(a).getName(), quantityList.get(a));
                    }
                    if (sell.containsKey(orderItemList.get(a).getName()) && orderItemList.get(a).getType().equals(type)) {
                        int newValue = sell.get(orderItemList.get(a).getName()) + quantityList.get(a);
                        sell.put(orderItemList.get(a).getName(), newValue);
                    }
                }

            }
        }
        for (HashMap.Entry<String, Integer> entry : sell.entrySet()) {
            if (entry.getValue() > bestSell) {
                bestSell = entry.getValue();
                bestSellItem = entry.getKey();
            }
        }
        return bestSellItem;
    }

    public static long getTotalSell(int shopId, Date oldDate, String type) {
        Date addDate = new Date();
        long totalSell = 0;
        List<Order> shopOrder = showAllOrdersByShopId(shopId);
        for (int i = 0; i < shopOrder.size(); i++) {
            if (shopOrder.get(i).getDate().getTime() > oldDate.getTime() && shopOrder.get(i).getDate().getTime() < addDate.getTime()) {
                List<Item> orderItemList = shopOrder.get(i).getItem();
                List<Integer> quantityList = shopOrder.get(i).getQuantity();
                for (int a = 0; a < quantityList.size(); a++) {
                    if (orderItemList.get(a).getType().equals(type)) {
                        totalSell += quantityList.get(a);
                    }
                }

            }
        }
        return totalSell;
    }

}