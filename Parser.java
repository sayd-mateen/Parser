/*
Name: Sayd Mateen
Class: CSC135

This program was compiled on Eclipse. It has be adjusted to work with the athena server.
To compile enter 'javac Parser.java' followed by the command 'java Parser.java'. Should compile and execuse without issues.
Enter the input without spaces, ending it with the '$' to idicate the end of the input. The program will then determine
weather the input is leagal or illeage. If determined illegal it will ouput the position of the error value within the
string. The location of the string starts at 0.


*/
import java.io.*;
import java.util.Scanner;

public class Parser {
	static String inputString;
	static int index = 0;
	static int errorflag = 0;
	static int prevError = 0;

	private static void match(char T){ if (T == token()) advancePtr(); else error(); }

	private static char token(){ return(inputString.charAt(index)); }

	private static void advancePtr(){
		if (index < (inputString.length()-1)){
			index = index + 1;
		}else if(errorflag == 1){
			System.out.println("Errors found." + "\n");
			System.exit(0);
		}
	}

	private static void error(){
		if(token() == '$' && errorflag == 1){
			System.out.println("Errors found." + "\n");
			System.exit(0);
		}else if(token() == '$' && errorflag == 0){
		    System.out.println("Legal." + "\n");
			System.exit(0);
		}else{
			System.out.println("Error at position: " + index);
			errorflag = 1;
			advancePtr();
		}
	}

	private static void letter(){
		if ((token() == 'X') || (token() == 'Y') || (token() == 'Z')) match(token()); else error();
	}

	private static void digit(){
		if ((token() == '0') || (token() == '1') || (token() == '2') || (token() == '3') || (token() == '4') || (token() == '5') || (token() == '6') || (token() == '7')) match(token()); else error();
	}

	private static void prodop(){
		if ((token() == '*') || (token() == '/')) match(token()); else error();
	}

	private static void sumop(){
		if ((token() == '+') || (token() == '-')) match(token()); else error();
	}

	private static void opratr(){
		if ((token() == '<') || (token() == '=') || (token() == '>') || (token() == '!')) match(token()); else error();
	}

	private static void output(){
		match('O');
		do{
			ident();
		}while(token() == ',');
		match(';');
	}

	private static void input(){
		match('N');
		do{
			ident();
		}while(token() == ',');
		match(';');
	}

	private static void forloop(){
		match('F');
		match('(');
		asignmt();
		match(')');
		match('(');
		comprsn();
		match(')');
		match('L');
		while(token() == 'X' || token() == 'Y' || token() == 'Z' || token() == 'I' || token() == 'F' || token() == 'N' || token() == 'O'){
			statemt();
		}
		match('\\');
	}

	private static void comprsn(){
		match('C');
		oprnd();
		opratr();
		oprnd();
		match(')');
	}

	private static void ifstmt(){
		match('I');
		//Test 
		comprsn();
		match('@');
		while(token() == 'X' || token() == 'Y' || token() == 'Z' || token() == 'I' || token() == 'F' || token() == 'N' || token() == 'O' || token() == '%'){
			 if(token() == '%'){
				 match('%');
				 while(token() == 'X' || token() == 'Y' || token() == 'Z' || token() == 'I' || token() == 'F' || token() == 'N' || token() == 'O' ){
					 statemt();
				 }
				 break;
			 }else{
				 statemt();
			 }
		}
		match('&');
	}

	private static void charCheck(){
		if(token() == 'X' || token() == 'Y' || token() == 'Z'){
			letter();
		}else{
			digit();
		}
	}

	private static void ident(){
		letter();
		while(token() == '0' || token() == '1' || token() == '2' || token() == '3' || token() == '4' || token() == '5' || token() == '6' || token() == '7' || token() == 'X' || token() == 'Y' || token() == 'Z'){
			charCheck();
		}
	}

	private static void integer(){
		do{
			digit();
		}while(token() == '0' || token() == '1' || token() == '2' || token() == '3' || token() == '4' || token() == '5' || token() == '6' || token() == '7');
	}


	private static void oprnd(){
		if(token() == '0' || token() == '1' || token() == '2' || token() == '3' || token() == '4' || token() == '5' || token() == '6' || token() == '7'){
			integer();
		}else if(token() == 'X' || token() == 'Y' || token() == 'Z'){
			ident();
		}else{
			match('C');
			exprsn();
			match(')');
		}
	}

	private static void factor(){
		oprnd();
		while(token() == '*' || token() == '/'){
			prodop();
			oprnd();
		}
	}


	private static void exprsn(){
		factor();
		while(token() == '+' || token() == '-'){
			sumop();
			factor();
		}
	}

	private static void asignmt(){
		ident();
		match('~');
		exprsn();
		match(';');
	}

	private static void statemt(){
		if(token() == 'X' || token() == 'Y' || token() == 'Z'){
			asignmt();
		}else if(token() == 'I'){
			ifstmt();
		}else if(token() == 'F'){
			forloop();
		}else if(token() == 'N'){
			input();
		}else{
			output();
		}
	}

	private static void program(){
		do{
			statemt();
		}while(token() == 'X' || token() == 'Y' || token() == 'Z' || token() == 'I' || token() == 'F' || token() == 'N' || token() == 'O');
	}

	private void start(){
	    program();
	    System.out.print("TEstgd ");
	    match('$');

	}

	public static void main(String[] args) throws IOException{
		Parcer rec = new Parcer();

	    Scanner input = new Scanner(System.in);

	    System.out.print("\n" + "Enter an expression: ");
	    inputString = input.nextLine();

	    rec.start();
		input.close();
	}

}
