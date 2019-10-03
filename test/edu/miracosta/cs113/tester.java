package edu.miracosta.cs113;
import edu.miracosta.cs113.DoubleLinkedList.Node;


import java.util.*;
import java.util.ListIterator;


public class tester {


    public static void main(String[] args) {
        List<Integer> list = new DoubleLinkedList<Integer>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        ListIterator<Integer> iter = list.listIterator();
        iter.next();
        iter.next();
        iter.next();

        iter.next();
        iter.next();


        Node<Integer> head = ((DoubleLinkedList<Integer>) list).head;
        Node<Integer> tail = ((DoubleLinkedList<Integer>) list).tail;

        while(head != null) {
            System.out.println(head.data);
            head = head.next;
        }

        System.out.println();

        while(tail != null) {
            System.out.println(tail.data);
            tail = tail.prev;
        }

        System.out.println();

        for (int i = 0; i < list.size(); i++) {
            System.out.println(iter.hasPrevious());
            System.out.println(list.get(i));
        }
    }
}
