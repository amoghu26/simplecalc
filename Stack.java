/**
 *	A homebrew Stack interface
 *	@author	Amogh Upadhyaya
 *  @since 25 March 2021
 */
public interface Stack<E> {
	
	/**	@return		true if the stack is empty; false otherwise */
	boolean isEmpty();
	/**	@return		the object on top of the stack */
	E peek();
	/**	@param obj	put the Object obj on top of the stack */
	void push(E obj);
	/**	@return		the object on top of the stack, and remove the object from the stack */
	E pop();
}