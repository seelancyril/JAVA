package blobxml;

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class TestBlobs {

    public static void main(String args[]){
        int x = 100;
        /*simple lambda function example*/
//        List newlist = Arrays.asList(2,4,1,8,5);
        List<Integer> newlist = new ArrayList<Integer>();
        for (int i=0; i<5;i++)
            newlist.add(i+5);
        List<Integer> oddNum =newlist.stream().map(a -> a*a).sorted().collect(Collectors.toList());
        System.out.println(oddNum);
    }

}
