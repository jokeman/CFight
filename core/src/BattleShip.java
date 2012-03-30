import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Dead_off
 * Date: 03.03.12
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
public class BattleShip {
//0 - о поле ничего не известно
//1 - в поле стреляли и непопали
//2- в поле есть корабль
//3- в поле стреляли и попали (корабль подбит)

    private final int[][] myShips;
    private final int[][] enemyShips;
    private final int[][] myShipsForEnemy;

    public BattleShip() {
        myShips = randomField();
        enemyShips = randomField();
        myShipsForEnemy = new int[10][10];
    }

    private int[][] randomField() {
        int[][] field = new int[10][10];
        final Random random = new Random();
        //придумываем случайно место для корабля и как он повернут (true - вертикально false - горизонтально)
        boolean flag = random.nextBoolean();
        int x;
        int y;
        if (flag) {
            x = random.nextInt(7);
            y = random.nextInt(10);
        } else {
            x = random.nextInt(10);
            y = random.nextInt(7);
        }
        //ставим корабль на его место
        int shipLength = 4;
        field = addShip(flag, field, shipLength, x, y);
        //4х палубник поставлен
        // ставим 3х палубники
        shipLength = 3;
        for (int i = 0; i < 2; i++) {
            flag = random.nextBoolean();
            x = random.nextInt(8);
            y = random.nextInt(8);
            if (flag) {
                //найдем место, куда можно поставить 3х палубник
                while (field[x + 1][y] != 0 || field[x + 2][y] != 0 || field[x][y] != 0) {
                    x = random.nextInt(8);
                    y = random.nextInt(10);
                }
                //нашли, ставим.
                field = addShip(flag, field, shipLength, x, y);
            } else {
                //найдем место, куда можно поставить 3х палубник
                while (field[x][y] != 0 || field[x][y + 1] != 0 || field[x][y + 2] != 0) {
                    x = random.nextInt(10);
                    y = random.nextInt(8);
                }
                //нашли, ставим.
                field = addShip(flag, field, shipLength, x, y);
            }
        }
        //2х палубники
        shipLength = 2;
        for (int i = 0; i < 3; i++) {
            flag = random.nextBoolean();
            x = random.nextInt(9);
            y = random.nextInt(9);
            if (flag) {
                //найдем место, куда можно поставить 2х палубник
                while (field[x + 1][y] != 0 || field[x][y] != 0) {
                    x = random.nextInt(9);
                    y = random.nextInt(10);
                }
                //нашли, ставим.
                field = addShip(flag, field, shipLength, x, y);
            } else {
                //найдем место, куда можно поставить 2х палубник
                while (field[x][y] != 0 || field[x][y + 1] != 0) {
                    x = random.nextInt(10);
                    y = random.nextInt(9);
                }
                //нашли, ставим.
                field = addShip(flag, field, shipLength, x, y);
            }
        }
        shipLength = 1;
        for (int i = 0; i < 4; i++) {
            x = random.nextInt(10);
            y = random.nextInt(10);
            while (field[x][y] != 0) {
                x = random.nextInt(10);
                y = random.nextInt(10);
            }
            field = addShip(flag, field, shipLength, x, y);
        }
        //удалим все 4рки в поле, они не нужны
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == 4) {
                    field[i][j] = 0;
                }
            }
        }
        //поле готово, можно возвращать
        return field;
    }

    public int[][][] shot(final int x, final int y) {
        if (x >= 10 || x < 0 || y < 0 || y >= 10) {
            System.out.println("Невозможный выстрел! x:" + x + " y:" + y);
        }
        if (myShips[x][y] == 2) {
            //Значит тут стоит корабль, соперник попал
            myShipsForEnemy[x][y] = 3;
            myShips[x][y] = 3;
            //сразу проверим, не победил ли соперник.
            if (isWin(myShips)) {
                //Соперник победил, так и скажем ему
                System.out.println("Поздавляем, вы победили!");
                //и вернем мое поле.
                return null;
            }
            //проверим сбит ли корабль, или его только ранили
            if (x + 1 <= 9) {
                if (myShips[x + 1][y] == 2) {
                    System.out.println("ранил:)");
                    return new int[][][]{enemyShips, myShipsForEnemy};
                }
            }
            if (y + 1 <= 9) {
                if (myShips[x][y + 1] == 2) {
                    System.out.println("ранил:)");
                    return new int[][][]{enemyShips, myShipsForEnemy};
                }
            }
            if (x - 1 >= 0) {
                if (myShips[x - 1][y] == 2) {
                    System.out.println("ранил:)");
                    return new int[][][]{enemyShips, myShipsForEnemy};
                }
            }
            if (y - 1 >= 0) {
                if (myShips[x][y - 1] == 2) {
                    System.out.println("ранил:)");
                    return new int[][][]{enemyShips, myShipsForEnemy};
                }
            }
            //рядом с кораблем нет 2, корабль потапили:)
            System.out.println("убил:)");
            //пока упращенный вариант, не будем обрисовывать вокруг корабля точки.
            return new int[][][]{enemyShips, myShipsForEnemy};
        }
        //иначе соперник промахнулся.
        System.out.println("Вы промахнулись!");
        myShips[x][y] = 1;
        myShipsForEnemy[x][y] = 1;
        //теперь стреляю я:)
        while (true) {
            //создадим лист возможных полей для выстрела
            ArrayList<String> arr = new ArrayList<String>();
            for (int i = 0; i < enemyShips.length; i++) {
                for (int j = 0; j < enemyShips.length; j++) {
                    if (enemyShips[i][j] != 1 && enemyShips[i][j] != 3) {
                        arr.add(i + ";" + j);
                    }
                }
            }
            //теперь выберем рандомно место для выстрела, и стрельнем:)
            String[] coordinat = arr.get(new Random().nextInt(arr.size())).split(";");
            int[] coordinats = new int[]{Integer.parseInt(coordinat[0]), Integer.parseInt(coordinat[1])};
            System.out.println("я стреляю в " + coordinats[0] + " " + coordinats[1]);
            if (enemyShips[coordinats[0]][coordinats[1]] != 2) {
                System.out.println("я не попал, пичально:(");
                enemyShips[coordinats[0]][coordinats[1]] = 1;
                return new int[][][]{enemyShips, myShipsForEnemy};
            } else {
                System.out.println("опа, попал:)");
                enemyShips[coordinats[0]][coordinats[1]] = 3;
                //сразу проверим, победил ли комп.
                if (isWin(enemyShips)) {
                    System.out.println("опа, я выиграл");
                    return new int[][][]{enemyShips, myShips};
                }
                System.out.println("стреляю ещё раз:)");
            }
        }
    }

    public void printField() {
        int i = 0;
        System.out.println("Ваше поле");
        for (int[] enemyShip : enemyShips) {
            System.out.println(i + " " + Arrays.toString(enemyShip));
            i++;
        }
        System.out.println("   0  1  2  3  4  5  6  7  8  9");
        i = 0;
        System.out.println("Мое поле");
        for (int[] enemyShip : myShipsForEnemy) {
            System.out.println(i + " " + Arrays.toString(enemyShip));
            i++;
        }
        System.out.println("   0  1  2  3  4  5  6  7  8  9");
    }

    private boolean isWin(final int[][] field) {
        for (int[] aField : field) {
            for (int j = 0; j < field.length; j++) {
                if (aField[j] == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] addShip(final boolean flag, final int[][] field, final int shipLength, final int x, final int y) {
        for (int i = 0; i < shipLength; i++) {
            //добавим временное значение 4, что означает, что поле свободно, но корабля в нем быть не может
            if (flag) {
                if (x - 1 >= 0) {
                    field[x - 1][y] = 4;
                }
                if (x - 1 >= 0 && y - 1 >= 0) {
                    field[x - 1][y - 1] = 4;
                }
                if (x - 1 >= 0 && y + 1 <= 9) {
                    field[x - 1][y + 1] = 4;
                }
                if (y - 1 >= 0) {
                    field[x + i][y - 1] = 4;
                }
                if (y + 1 <= 9) {
                    field[x + i][y + 1] = 4;
                }
                if (x <= field.length-shipLength-1 && y + 1 <= 9) {
                    field[x + shipLength][y + 1] = 4;
                }
                if (x <= field.length-shipLength-1) {
                    field[x + shipLength][y] = 4;
                }
                if (x <= field.length-shipLength-1 && y - 1 >= 0) {
                    field[x + shipLength][y - 1] = 4;
                }
                field[x + i][y] = 2;
            } else {
                if (x - 1 >= 0 && y - 1 >= 0) {
                    field[x - 1][y - 1] = 4;
                }
                if (x + 1 <= 9 && y - 1 >= 0) {
                    field[x + 1][y - 1] = 4;
                }
                if (y - 1 >= 0) {
                    field[x][y - 1] = 4;
                }
                if (x + 1 <= 9) {
                    field[x + 1][y + i] = 4;
                }
                if (x - 1 >= 0) {
                    field[x - 1][y + i] = 4;
                }
                if (x - 1 >= 0 && y <= field.length-shipLength-1) {
                    field[x - 1][y + shipLength] = 4;
                }
                if (y <= field.length-shipLength-1) {
                    field[x][y + shipLength] = 4;
                }
                if (x + 1 <= 9 && y <= field.length-shipLength-1) {
                    field[x + 1][y + shipLength] = 4;
                }
                field[x][y + i] = 2;
            }
        }
        return field;
    }
}
