package ccc;
import java.util.*;
import java.util.Scanner;
public class MazeProblem{
    static int smax=100000;
    static int top=-1;
    static int n, count=0, min_dist=9999999;
    static char[] possiblePaths=new char[smax];
    static char[] path=new char[smax];
    static void push(char x){  
        if(top == smax-1)
            System.out.println("stack overflow");  
        else
          possiblePaths[++top]=x;  
    } 
    static void pop(){    
        //possiblePaths[top];
        top--;
    }
    static int isValidCell(int x, int y){
        if (x < 0 || y < 0 || x >= n || y >= n) 
            return 0;
        return 1;
    }
    static int isSafe(int row, int col, int[][] m, int n, int[][] visited){
	    if (row == -1 || row == n || col == -1 || col == n || visited[row][col] ==1 || m[row][col] == 0)
		    return 0;
	    return 1;
    }
    static int safe(int[][] mat, int[][] visited, int x, int y){
        if (mat[x][y] == 0 || visited[x][y]==1) {
            return 0;
        }
        return 1;
    }

    static void findShortestPath(int[][] mat, int[][] visited, int i, int j, int x, int y, int dist){
        if (i == x && j == y){
            if(dist < min_dist)
                min_dist = dist;
            return;
        }
        visited[i][j] = 1;
        if (isValidCell(i + 1, j) == 1 && safe(mat, visited, i + 1, j) == 1) {
            findShortestPath(mat, visited, i + 1, j, x, y, dist + 1);
        }
        if (isValidCell(i, j + 1) == 1 && safe(mat, visited, i, j + 1) == 1) {
            findShortestPath(mat, visited, i, j + 1, x, y, dist + 1);
        }
        if (isValidCell(i - 1, j) == 1 && safe(mat, visited, i - 1, j) == 1) {
            findShortestPath(mat, visited, i - 1, j, x, y, dist + 1);
        }
        if (isValidCell(i, j - 1) == 1 && safe(mat, visited, i, j - 1) == 1) {
            findShortestPath(mat, visited, i, j - 1, x, y, dist + 1);
        }
        visited[i][j] = 0;
    }

    static void countPaths(int[][] maze, int x, int y, int[][] visited){
        if (x == n - 1 && y == n - 1){
            count++;
            return;
        }
        visited[x][y] = 1;
        if (isValidCell(x, y) == 1 && maze[x][y] == 1){
            if (x + 1 < n && visited[x + 1][y] == 0) {
                countPaths(maze, x + 1, y, visited);
            }
            if (x - 1 >= 0 && visited[x - 1][y] == 0) {
                countPaths(maze, x - 1, y, visited);
            }
            if (y + 1 < n && visited[x][y + 1] == 0) {
                countPaths(maze, x, y + 1, visited);
            }
            if (y - 1 >= 0 && visited[x][y - 1] == 0) {
                countPaths(maze, x, y - 1, visited);
            }
        }
        visited[x][y] = 0;
    }

    static void printPathUtil(int row, int col, int[][] m, int n, int[][] visited){
	    if (row == -1 || row == n || col == -1 || col == n || visited[row][col] == 1 || m[row][col] == 0)
		    return;

	    if (row == n - 1 && col == n - 1) {
		    for(int i=0; i<=top; i++){
		        System.out.print(possiblePaths[i]);
		    }
		    if(top == min_dist-1)
	            System.out.print(" --> Shortest path");
	        System.out.println();
		    int[][] sol=new int[n][n];
		    for(int i = 0; i < n; i++){
		        for(int j = 0; j < n; j++){
		            sol[i][j] = 0;
		        }
		    }
		    int i = 0;
		    int j = 0;
		    sol[i][j] = 1;
		    for(int k = 0; k <= top; k++){
		        if(possiblePaths[k] == 'D'){
		            sol[++i][j] = 1;
		        }
		        if(possiblePaths[k] == 'R'){
		            sol[i][++j] = 1;
		        }
		        if(possiblePaths[k] == 'L'){
		            sol[i][--j] = 1;
		        }
		        if(possiblePaths[k] == 'U'){
		            sol[--i][j] = 1;
		        }
		    }
		    for(int x = 0; x < n; x++){
		        for(int y = 0; y < n; y++){
		            System.out.print(sol[x][y]+" ");
		        }
		        System.out.println();
		    }
		    System.out.println();
		    return;
	    }
	    visited[row][col] = 1;
	
	    if (isSafe(row + 1, col, m, n, visited) == 1){
		    push('D');
		    printPathUtil(row + 1, col, m, n, visited);
		    pop();
	    }
	
	    if (isSafe(row, col - 1, m, n, visited) == 1){
		    push('L');
		    printPathUtil(row, col - 1, m, n, visited);
		    pop();
	    }
	
	    if (isSafe(row, col + 1, m, n, visited) == 1){
		    push('R');
		    printPathUtil(row, col + 1, m, n, visited);
		    pop();
	    }
	
	    if (isSafe(row - 1, col, m, n, visited) == 1){
		    push('U');
		    printPathUtil(row - 1, col, m, n, visited);
		    pop();
	    }
	    visited[row][col] = 0;
    }

    static void printPath(int[][] m, int n){
	    char[] path=new char[smax];
	    int[][] visited=new int[n][n];
	    for(int i = 0; i < n; i++){
	        for(int j = 0; j < n; j++){
	            visited[i][j] = 0;
	        }
	    }
	    printPathUtil(0, 0, m, n, visited);
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the size of the maze : ");
        n=sc.nextInt();
        int[][] m=new int[n][n];
        System.out.println("Enter the maze: ");
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < n; j++){
                m[i][j]=sc.nextInt();
            }
        }
        int[][] visited=new int[n][n];
        for(int i = 0; i < n; i++){
	        for(int j = 0; j < n; j++){
	            visited[i][j] = 0;
	        }
	    }
	    countPaths(m, 0, 0, visited);
	    System.out.println("The number of unique paths for the given maze is : "+ count);
        findShortestPath(m, visited, 0, 0, n-1, n-1, 0);
        for(int i = 0; i < n; i++){
	        for(int j = 0; j < n; j++){
	            visited[i][j] = 0;
	        }
	    }
	    System.out.println("All possible paths are :");
	    printPath(m, n);
    }
}