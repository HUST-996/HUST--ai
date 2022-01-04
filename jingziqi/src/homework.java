import java.util.Scanner;

public class homework {
    char[] chess = new char[10];
    Scanner scanner = new Scanner(System.in);

    //玩家用X，电脑用O
    char player = 'X';
    char computer = 'O';
    //设置棋盘初始界面
    public void setChess() {
        for(int i = 1; i <= 9; i++){
            chess[i] = '-';
        }
    }

    //打印棋盘
    public void print(){
        for(int i = 1;i <= 9; i++){
            System.out.print(chess[i]+" ");
            if(i%3 == 0) System.out.println();
        }
    }

    //玩家下棋
    public void playerTurn(){
        while (true){
            System.out.println("请选择所下棋子的位置：(1-9)");
            int pos = scanner.nextInt();
            if(chess[pos] != '-'){
                System.out.println("该位置已经有棋子，请重新选择：(1-9)");
            }
            else {
                chess[pos] = player;
                break;
            }
        }
            print();
    }

    //电脑下棋
    public void computerTurn(){
        int pos = 0;
        int bestScore=-1000;
        int score;
        for(int i = 1; i <= 9; i++){
            if(chess[i] == '-'){
                chess[i] = computer;
                score = alpha_beta_score(player,computer,-1000,1000);
                if(score > bestScore){
                    bestScore = score;
                    pos = i;
                }
                chess[i] = '-';
            }
        }
        chess[pos] = computer;
        System.out.println("电脑下棋如下：");
        print();
    }
    //用alpha-beta剪枝算法来实现井字棋
    public int alpha_beta_score(char pl,char next_pl, int alpha, int beta){
        //局势判断
        if(judgeVD() == 1) return -1;
        else if(judgeVD() == 2) return 1;
        else if(judgeVD() == 3) return 0;
        else{
            for(int i = 1; i <= 9; i++){
                if(chess[i] == '-'){
                    chess[i] = pl;
                    int score = alpha_beta_score(next_pl,pl,alpha,beta);
                    chess[i] = '-';
                    if(pl == computer){
                        if(score > alpha) alpha = score;
                        if(beta <= alpha) return beta;
                    }
                    else{
                        if(score < beta) beta = score;
                        if(beta <= alpha) return alpha;
                    }
                }
            }
            if(pl == computer) return alpha;
            else return beta;
        }
    }
    //判断棋局终止
    public int judgeVD(){
        //玩家获胜，返回1。
        if(chess[1] == player && chess[2] == player && chess[3] == player) return 1;
        if(chess[4] == player && chess[5] == player && chess[6] == player) return 1;
        if(chess[7] == player && chess[8] == player && chess[9] == player) return 1;
        if(chess[1] == player && chess[4] == player && chess[7] == player) return 1;
        if(chess[2] == player && chess[5] == player && chess[8] == player) return 1;
        if(chess[3] == player && chess[6] == player && chess[9] == player) return 1;
        if(chess[1] == player && chess[5] == player && chess[9] == player) return 1;
        if(chess[3] == player && chess[5] == player && chess[7] == player) return 1;

        //电脑获胜，返回2。
        if(chess[1] == computer && chess[2] == computer && chess[3] == computer) return 2;
        if(chess[4] == computer && chess[5] == computer && chess[6] == computer) return 2;
        if(chess[7] == computer && chess[8] == computer && chess[9] == computer) return 2;
        if(chess[1] == computer && chess[4] == computer && chess[7] == computer) return 2;
        if(chess[2] == computer && chess[5] == computer && chess[8] == computer) return 2;
        if(chess[3] == computer && chess[6] == computer && chess[9] == computer) return 2;
        if(chess[1] == computer && chess[5] == computer && chess[9] == computer) return 2;
        if(chess[3] == computer && chess[5] == computer && chess[7] == computer) return 2;

        //平局，返回3。
        for(int i = 1; i <= 9; i++){
            if(chess[i] == '-') return 0;//棋局未结束，返回0。
        }
        return 3;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        homework h = new homework();
        h.setChess();
        int choice;
        System.out.println("请选择先手还是后手：(输入1为先手，输入2为后手)");
        while(true){
            choice = scanner.nextInt();
            if(choice == 1 || choice == 2) break;
            else {
                System.out.println("输入错误，请重新输入：(输入1为先手，输入2为后手)");
            }
        }

        for(int i = 1; i <= 2; i = i%2+1){
            if(i == choice) h.playerTurn();
            else h.computerTurn();
            //棋局结束，跳出循环
            if(h.judgeVD() != 0){
                break;
            }
        }
        switch (h.judgeVD()){
            case 1:
                System.out.println("玩家获胜！");
                break;
            case 2:
                System.out.println("电脑获胜！");
                break;
            case 3:
                System.out.println("平局！");
                break;
            default:
                break;
        }
    }
}
