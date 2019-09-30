package edu.miracosta.cs113;

import java.util.ListIterator;
import java.util.AbstractSequentialList;
import java.util.NoSuchElementException;

public class DoubleLinkedList<E> extends AbstractSequentialList<E>
{
    /************************ Instance variables ****************************/
    private Node<E> head = null ;               // Reference to head of the list
    private Node<E> tail = null ;               // Reference to tail end of the list
    private int size = 0;                       // Number of nodes in the list

    /**************************** Methods *******************************/

    // Add an item at the specified index.
    @Override
    public void add(int index, E obj) {
        listIterator(index).add(obj);
    }

    // Get the element at position index.
    @Override
    public E get(int index) {
        return listIterator(index).next();
    }

    // Return the size of the list
    @Override
    public int size() {
        return size;
    }
    /********************************** Inner Classes*******************************/
    // A Node is the building block for a double-linked list.
    private static class Node<E> {

        private E data;                   // data value in the node
        private Node<E> next = null;      // line to next node in the list
        private Node<E> prev = null;      // link to previous node in the list

        /**
         * Construct a node with the given data value.
         * @param dataItem The data value
         */
        private Node(E dataItem)
        {
            data = dataItem;
        }
    } //end class Node

    //Ervin's code for this project
    //method called ListIterator
    //what it does is it returns a new KWListIter(index) to the user
    public ListIterator<E> listIterator(int index)
    {
        return new KWListIter(index);
    }
    /** Inner class to implement the ListIterator interface. */
    private class KWListIter implements ListIterator<E> {

        // Instance variables
        private Node<E> nextItem ;                  // Reference to next item in the iterator
        private Node<E> lastItemReturned;           // Reference to the last item returned by iterator
        private int index = 0;                      // Index of the current item

        /**
         * Construct a KWListIter that will reference the ith item.
         * @param i The index of the item to be referenced
         */
        public KWListIter(int i) {
            // Validate i parameter.
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException();
            }
            lastItemReturned = null; // No item returned yet.
            // Special case of last item.
            if (i == size)
            {
                index = size;
                nextItem = null;
            }
            else { // Start at the beginning
                nextItem = head;
                for (index = 0; index < i; index++) {
                    nextItem = nextItem.next;
                    //code Jose helped me with.
                    lastItemReturned = nextItem.prev;
                }
            }
        }
        //Ervin's code for public set(E object)
        public void set(E object)
        {

            if (lastItemReturned == null)
            {
                throw new IllegalStateException();
            }
            lastItemReturned.data = object;
        }
        //
        /*
         *Ervin's attempt at a remove method
         *First if node to be removed is head node set its next node to be head.
         *Second set the previous node's next to null.
         *Third set the .prev of the next node to null if that node exists
         */
        public void remove()
        {
           if (head == null||lastItemReturned == null)
           {
               return;
           }
           // If node to be deleted is head node
           if (head == lastItemReturned)
           {
               head = head.next;
           }
           // Change next only if node to be deleted
            // is NOT the last node
           if (lastItemReturned.next != null)
           {
               lastItemReturned.next.prev = lastItemReturned.prev;
           }
           if (lastItemReturned.prev != null)
           {
               nextItem.prev = lastItemReturned.prev;
           }

            size--;

           return;
        }

        public void clear()
        {
            Node<E> node = head;
            while(node!=null)
            {
                Node<E> assistantNode = node.next;
                node.next = node.prev =null;

                node = assistantNode;
            }
            size = 0;
            index++;
        }
        /**
         * Construct a KWListIter that is a copy of another KWListIter
         * @param other The other KWListIter
         */
        public KWListIter(KWListIter other) {
            KWListIter itr = new KWListIter(0);
            itr.index = other.index;
            itr.lastItemReturned = other.lastItemReturned;
            itr.nextItem = other.nextItem;
        }

        /**
         * Indicate whether movement forward is defined.
         * @return true if call to next will not throw an exception
         */
        @Override
        public boolean hasNext() {
            return nextItem != null;
        }

        /** Move the iterator forward and return the next item.
         @return The next item in the list
         @throws NoSuchElementException if there is no such object
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException();
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        /**
         * Indicate whether movement backward is defined.
         * @return true if call to previous will not throw an exception
         */
        @Override
        public boolean hasPrevious() {

            return (nextItem == null && size != 0)
                    || nextItem.prev != null;
        }

        /**
         * Return the index of the next item to be returned by next
         * @return the index of the next item to be returned by next
         */
        @Override
        public int nextIndex() {
            return index;
        }

        /**
         * Return the index of the next item to be returned by previous
         * @return the index of the next item to be returned by previous
         */
        @Override
        public int previousIndex() {
            return index - 1;
        }

        /**
         * Move the iterator backward and return the previous item.
         * @return The previous item in the list
         * @throws NoSuchElementException if there is no such object
         */
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (nextItem == null) { // Iterator past the last element
                nextItem = tail;
            } else {
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        /**
         * Add a new item between the item that will be returned
         * by next and the item that will be returned by previous.
         * If previous is called after add, the element added is
         * returned.
         * @param obj The item to be inserted
         */
        @Override
        public void add(E obj) {
            if (head == null) { // Add to an empty list.
                head = new Node<E>(obj);
                tail = head;
            } else if (nextItem == head) { // Insert at head.
                // Create a new node.
                Node<E> newNode = new Node<E>(obj);
                // Link it to the nextItem.
                newNode.next = nextItem; // Step 1
                // Link nextItem to the new node.
                nextItem.prev = newNode; // Step 2
                // The new node is now the head.
                head = newNode; // Step 3
            } else if (nextItem == null) { // Insert at tail.
                // Create a new node.
                Node<E> newNode = new Node<E>(obj);
                // Link the tail to the new node.
                tail.next = newNode; // Step 1
                // Link the new node to the tail.
                newNode.prev = tail; // Step 2
                // The new node is the new tail.
                tail = newNode; // Step 3
            } else { // Insert into the middle.
                // Create a new node.
                Node<E> newNode = new Node<E>(obj);
                // Link it to nextItem.prev.
                newNode.prev = nextItem.prev; // Step 1
                nextItem.prev.next = newNode; // Step 2
                // Link it to the nextItem.
                newNode.next = nextItem; // Step 3
                nextItem.prev = newNode; // Step 4
            }
            // Increase size and index and set lastItemReturned.
            size++;
            index++;
            lastItemReturned = null;
        } // End of method add.

    } //end class KWListIter
}
