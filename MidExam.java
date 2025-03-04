// 컴퓨터공학부(컴퓨터공학전공) 201935315 이시윤
import java.util.Scanner;

public class MidExam {
    Scanner scan = new Scanner(System.in);

    // 카테고리별 물품
    String[][] productMenu = {
            {"식품", "생활용품", "전자제품", "의류", "메인 메뉴로 가기"},
            {"쌀", "빵", "우유", "과일", "채소", "고기", "생선"},
            {"휴지", "세제", "비누", "샴푸", "칫솔", "청소용품", "수건"},
            {"TV", "냉장고", "세탁기", "노트북", "청소기"},
            {"셔츠", "바지", "양말", "신발", "모자", "후드티"}
    };

    // 카테고리별 물품 가격
    int[][] productPrice = {
            {0, 1, 2, 3, 4},
            {10000, 2000, 1500, 5000, 6000, 20000, 12000},
            {3000, 7000, 1000, 8000, 1500, 4000, 2500},
            {800000, 2000000, 1200000, 1500000, 500000},
            {20000, 25000, 4000, 49000, 14000, 35000}
    };

    // 메인 메뉴
    String[] mainMenu = {"상품 보기", "장바구니 조회", "결제하기", "리뷰 콘텐츠"};

    String[] shopCartProduct = new String[10];  // 최대 10 종류의 물품을 담을 수 있는 장바구니(물품) 배열
    int[] shopCartQuantity = new int[10];       // 최대 10 종류의 물품의 가격을 담을 수 있는 장바구니(가격) 배열
    int cartCount = 0;                          // 장바구니에 몇 종류의 물품을 담았는지 세는 변수
    int sel;                                    // 물품의 어느 카테고리를 선택했는지 담는 변수
    int productNumber;                          // 어떤 제품을 선택했는지 담는 변수
    int productQuantity;                        // 제품의 개수를 담는 변수
    int totalPrice = 0;                         // 총 결제금액을 담는 변수
    String[][] reviewArray = new String[20][2]; // 리뷰의 제품명과 리뷰 내용을 담을 배열
    int reviewCount = 0;                        // 담은 리뷰의 수
    String[] customerInfo = new String[2];      // 고객의 성명과 주소를 담는 배열

    // run 함수 실행 시 getAddress() 실행
    // 사용자의 성명, 주소 정보를 얻고 showMainMenu()를 통해 메인 메뉴 출력
    public void run() {
        getAddress();
        showMainMenu();
    }

    // 사용자 정보(성명, 주소)를 얻음
    // 잘못입력했을 때를 대비하여 while 문 안에서 반복
    // 제대로 입력했다면 ok값을 false로 만들고 while 문을 빠져나간다.
    // 틀리게 입력했다면 다시 입력받는다.
    public void getAddress() {
        System.out.println(" [ On-Shop에 오신 것을 환영합니다 ]");
        System.out.println(" [ 주문에 앞서 고객님의 주소 정보를 입력해주세요. ]");
        boolean ok = true;
        int check;

        while (ok) {
            System.out.print("고객명 : ");
            customerInfo[0] = scan.nextLine();
            System.out.print("주소 : ");
            customerInfo[1] = scan.nextLine();
            System.out.println("[1] : 입력 완료  ||  [2] : 다시 입력(잘못 입력 시)");
            System.out.print("[1 or 2] 입력 : ");
            check = scan.nextInt();
            scan.nextLine();

            if (check == 1) {
                ok = false;
            } else if (check == 2) {
                continue;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 메인 메뉴를 보여주는 메서드, 실행이 끝날 대 mainMenu의 옵션을 선택할 수 있는 메서드 mainMenuSelect() 메서드를 실행한다.
    public void showMainMenu() {
        System.out.println("-----------------------------------------------------------------");
        System.out.print("|| ");

        for(int i = 0; i < mainMenu.length; i++) {
            System.out.print((i+1) + " : " + mainMenu[i] + " || ");
        }

        System.out.println();
        System.out.println("-----------------------------------------------------------------");
        mainMenuSelect();
    }

    // 메인메뉴의 상품보기, 장바구니 조회, 결제하기, 세 가지 메뉴 중 사용자가 원하는 메뉴를 입력받아 각각의 메서드 실행
    // 사용자가 잘못 입력했을 때를 대비해 default 값으로 showMainMenu를 설정
    public void mainMenuSelect() {
        int goMenu;
        System.out.print("[1 or 2 or 3 or 4] 입력 : ");
        goMenu = scan.nextInt();
        System.out.println();

        switch (goMenu) {
            case 1 -> showMenu();
            case 2 -> checkOrder();
            case 3 -> checkOut();
            case 4 -> reviewContents();
            default -> {
                System.out.println("잘못된 입력입니다 !!");
                showMainMenu();
            }
        }
    }

    // 제품 카테고리명을 출력하고 제품 카테고리별 물품을  출력할 showProduct() 메서드 실행
    public void showMenu() {
        System.out.println("[ 카테고리 ]");
        System.out.println("-----------------------------------------------------------------------");
        System.out.print("|| ");

        for (int i = 0; i < productMenu[0].length; i++) {
            System.out.print( (i+1) + " : "+ productMenu[0][i] + " || ");
        }

        System.out.println();
        System.out.println("-----------------------------------------------------------------------");
        showProduct();
    }

    // 검색하고자 하는 카테고리를 입력받고 해당하는 카테고리 물품을 출력
    // 장바구니에 담고자 하는 물품이 있을 수 있으므로 addToShopCart() 실행
    private void showProduct() {
        System.out.print("[1 or 2 or 3 or 4 or 5] 입력 : ");
        sel = scan.nextInt();

        if (sel == 5) {
            showMainMenu();
        }

        System.out.println("-------제품목록-------");

        for (int i = 0; i < productMenu[sel].length; i++) {
            System.out.println((i+1) + ". " + productMenu[sel][i] + " : " + productPrice[sel][i] + "원");
        }

        System.out.println("-------------------");
        addToShopCart();
    }

    // 장바구니에 물건을 담을지 질의하는 메서드
    // while 문으로 사용자의 응답을 받음. 1을 입력받으면 물품을 담는 selProduct() 메서드 실행.
    // 장바구니의 물품 종류가 10종류를 넘으면 안되기 때문에 계속해서 cartCount 를 확인하고,
    // 2와 그 외 응답을 입력받으면 ok 불리언이 false로 설정하고 무한 while 문을 빠져나가 showMenu()를 실행
    private void addToShopCart() {
        boolean ok = true;
        int choice;

        while(ok) {
            if(cartCount > 9) {
                fullCart();
                break;
            }
            System.out.println(">>> [1]. 장바구니에 담기  ||  [2]. 뒤로 가기 <<<");
            System.out.print("[1 or 2] 입력 : ");
            choice = scan.nextInt();

            if (choice == 1) {
                selProduct();
            } else if (choice == 2) {
                ok = false;
            } else {
                System.out.println("잘못된 입력입니다.");
                ok = false;
            }
        }
        showMenu();
    }

    // cartCount가 10이 되면 실행되는 메서드로 장바구니가 가득 찼다는 경고와 주문 결제 여부 checkOrder() 메서드로 질의.
    private void fullCart() {
        System.out.println("장바구니가 가득 찼습니다!! 먼저 결제를 진행해주세요 !!");
        checkOrder();
    }

    // 구매하고 하는 물품의 번호와 개수를 입력받아 shopCartProduct에 물품명을, shopCartQuantity에 개수를 저장하고, cartCount를 1 증가시킴
    private void selProduct() {
        System.out.print("물품번호 : ");
        productNumber = scan.nextInt();
        System.out.print("개수 : ");
        productQuantity = scan.nextInt();
        shopCartProduct[cartCount] = productMenu[sel][productNumber-1];
        shopCartQuantity[cartCount] = productQuantity;
        cartCount++;
        totalPrice += productQuantity * productPrice[sel][productNumber-1];
    }

    // 장바구니 목록을 조회하는 메서드로 cartCount값을 기준으로 반복문 출력
    // 장바구니에 담은 제품명과, 개수, 총 결제 금액을 출력한다.
    public void checkOrder() {
        System.out.println("   [장바구니 목록]");
        System.out.println("-------------------");

        for(int i = 0; i < cartCount; i++) {
            System.out.println("제품명 : " + shopCartProduct[i] + ", 개수 : " + shopCartQuantity[i]);
        }
        System.out.println("-------------------");
        System.out.println("총 주문금액 : " + totalPrice);
        isCheckOut();
    }

    // 장바구니 조회를 하고 곧바로 결제를 원할 수 있으므로 isCheckOut() 메서드를 실행
    // 결제를 희망하면 1, 아니라면 2를 입력받고, 1를 입력받으면 결제 메서드 실행
    // 2와 그 외 번호를 입력받으면 showMainMenu() 실행
    private void isCheckOut() {
        int checkSel;
        System.out.println("상품을 결제하시겠습니까?");
        System.out.println();
        System.out.println(">>> [1] : 결제하기  ||  [2] : 메뉴로 가기 <<<");
        System.out.print("[1 or 2] 입력 : ");
        checkSel = scan.nextInt();

        if (checkSel == 1) {
            checkOut();
        } else if (checkSel == 2) {
            showMainMenu();
        } else {
            System.out.println("잘못된 입력입니다 !!");
            showMainMenu();
        }
    }

    // 결제 메서드
    // 총 결제 금액을 출력하며 고객이 처음 입력한 고객명과 주소 정보를 가지고 해당하는 주소지로 배송을 예고함
    public void checkOut() {
        System.out.println("-------------------");
        System.out.println(">> 결제를 진행합니다 <<");
        System.out.println("-------------------");
        System.out.println("> 총 결제 금액 : " + totalPrice + " <");
        System.out.println("!! 주문완료 !!");
        System.out.println(customerInfo[0] + "님의 주소, " + customerInfo[1] + "로 주문하신 상품이 배송됩니다!!");
        System.exit(0);
    }

    // 상품들에 대한 리뷰를 남기는 메서드.
    // 제품명과 내용을 입력받아 배열에 저장한다.
    private void addReview() {
        System.out.print("리뷰를 남길 제품명을 입력하세요 : ");
        reviewArray[reviewCount][0] = scan.nextLine();
        System.out.print("리뷰를 남겨주세요 : ");
        reviewArray[reviewCount][1] = scan.nextLine();
        System.out.println("제품 " + reviewArray[reviewCount][0] + "에 대한 리뷰가 등록되었습니다.");
        reviewCount++;
        showMainMenu();
    }

    // 남긴 리뷰를 볼 수 있는 showReview() 메서드.
    // 작성한 리뷰의 내용을 출력한다.
    private void showReview() {
        System.out.println(" [제품명] : [리뷰 내용]");

        for(int i = 0; i < reviewCount; i++) {
            System.out.println(reviewArray[i][0] + " : " + reviewArray[i][1]);
        }
        showMainMenu();
    }

    // 메인 메뉴에서 리뷰 콘텐츠 카테고리인 4를 입력받으면 연결되는 메서드
    // 리뷰 쓰기, 리뷰 조회하기 메뉴를 제공한다.
    private void reviewContents() {
        System.out.println("========== 리뷰 콘텐츠 ==========");
        System.out.println(">> [1] : 리뷰 쓰기 || [2] : 리뷰 조회하기 <<");
        System.out.print("[1 or 2] 입력 : ");
        int select = scan.nextInt();
        scan.nextLine();

        switch (select) {
            case 1 -> addReview();
            case 2 -> showReview();
            default -> {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static void main(String[] args) {
        MidExam app = new MidExam();
        app.run();
    }
}