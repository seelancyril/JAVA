package com.zoho;

public class ZohoQuestionOne {
	int LHS(int a[], int index){
        //Sum of left side values
        int sum=0;
        for(int i=0; i<=index-1; i++){
            sum = sum + a[i];
        }
        return sum;
    }
    
    int RHS(int a[], int index){
        //sum of right side values
        int sum=0;
        for(int i=index+1; i<a.length; i++){
            sum = sum + a[i];
        }
        return sum;
    }
    
    public static void main(String[] args){
        int arr[] = {2,2,4,3,1};
        int given_index = 2;
        int index_val = arr[given_index];
        ZohoQuestionOne z = new ZohoQuestionOne();
        int left = z.LHS(arr, given_index);
        int right = z.RHS(arr, given_index);
        
        if (left == right && right == index_val){
            System.out.println("Yes");
        }
        else{
            System.out.println("No");
        }
    }
}
