package textgen;

import java.util.AbstractList;

//import javax.swing.text.html.HTMLEditorKit.LinkController;

//import com.sun.org.apache.xml.internal.serializer.ElemDesc;

//import javafx.scene.chart.PieChart.Data;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		LLNode<E> a = new LLNode<E>(null);
		LLNode<E> b = new LLNode<E>(null);
		head = a;
		tail = b;
		a.next = b;
		b.prev = a;
		size = 0;
	
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		if(element == null){
			throw new NullPointerException();
		}
		LLNode<E> lln = new LLNode<E>(element);

		lln.prev = tail.prev;
		lln.next = tail;
		tail.prev.next = lln;
		tail.prev = lln;

		size++;
		// TODO: Implement this method
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if(index < 0 || index > size -1){
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> ptr = head;
		for(int i = 0; i <= index; i++){
			ptr = ptr.next;
		}
		// TODO: Implement this method.
		return ptr.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if(index < 0 || (index > size && index > 0)){
			throw new IndexOutOfBoundsException();
		}
		if(element == null){
			throw new NullPointerException();
		}
		LLNode<E> ptr = head;
		for(int i = 0; i <= index; i++){
			ptr = ptr.next;
		}
		LLNode<E> lle = new LLNode<E>(element);
		lle.prev = ptr.prev;
		lle.next = ptr;
		ptr.prev.next = lle;
		ptr.prev = lle;
		size++;
 		// TODO: Implement this method
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if(index < 0 || index > size -1){
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> ptr = head;
		for(int i = 0; i <= index; i++){
			ptr = ptr.next;
		}
		ptr.prev.next = ptr.next;
		ptr.next.prev = ptr.prev;
		E data = ptr.data; 
		ptr = null;
		size--;
		// TODO: Implement this method
		return data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(index < 0 || index > size -1){
			throw new IndexOutOfBoundsException();
		}
		if(element == null){
			throw new NullPointerException();
		}

		LLNode<E> ptr = head;
		for(int i = 0; i <= index; i++){
			ptr = ptr.next;
		}
		E data = ptr.data;
		ptr.data = element;
		// TODO: Implement this method
		return data;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e, LLNode<E> p, LLNode<E> n) 
	{
		this.data = e;
		this.prev = p;
		this.next = n;
	}

}
