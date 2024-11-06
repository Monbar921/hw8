package org.example;


import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {
        Tree<Double> tree = new Tree<>();
        Double result = 0.0;
        for (String action : actionList) {
            String[] actionArray = action.split(" ");
            switch (actionArray[0]) {
                case "BID": {
                    tree.add(Double.valueOf(actionArray[1]));
                    break;
                }
                case "DEL": {
                    tree.remove(Double.valueOf(actionArray[1]));
                    break;
                } case "SALE": {
                    result += tree.getProfit(Double.valueOf(actionArray[1]), Integer.valueOf(actionArray[2]));
                    break;
                }
            }

        }
        return result;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1">https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1</a><br/>
     */
    public List<Integer> getLeaveOrder(List<String> actionList) {
        Tree<Integer> tree = new Tree<>();
        List<Integer> result = new ArrayList<>();
        int y = 0;
        for(String action: actionList) {
            String[] actionArray = action.split(" ");
            switch (actionArray[0]) {
                case "+": {
                    tree.add((int) ((y + Integer.parseInt(actionArray[1])) % Math.pow(10, 9)));
                    y = 0;
                    break;
                }
                case "?": {
                    y = (Integer) tree.next(Integer.parseInt(actionArray[1]));
                    result.add(y);
                    break;
                }
            }
        }
        return result;
    }

}
