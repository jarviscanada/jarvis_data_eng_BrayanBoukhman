class DoubleLinkedListNode<T> {
    value: T;
    next: DoubleLinkedListNode<T> | null = null;
    prev: DoubleLinkedListNode<T> | null = null;

    constructor(value: T) {
        this.value = value;
    }
}

class DoublyLinkedList<T> {
    private head: DoubleLinkedListNode<T> | null = null;
    private tail: DoubleLinkedListNode<T> | null = null;

    push(value: T): void {
        const newNode = new DoubleLinkedListNode(value);

        if (!this.head) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.prev = this.tail;
            this.tail!.next = newNode;
            this.tail = newNode;
        }
    }

    pop(): T | undefined {
        if (!this.tail) {
            return undefined;
        }

        const poppedValue = this.tail.value;

        if (this.head === this.tail) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = this.tail.prev;
            this.tail!.next = null;
        }

        return poppedValue;
    }

    shift(): T | undefined {
        if (!this.head) {
            return undefined;
        }

        const shiftedValue = this.head.value;

        if (this.head === this.tail) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.next;
            this.head!.prev = null;
        }

        return shiftedValue;
    }

    unshift(value: T): void {
        const newNode = new DoubleLinkedListNode(value);

        if (!this.head) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.next = this.head;
            this.head!.prev = newNode;
            this.head = newNode;
        }
    }
}

const doublyLinkedList = new DoublyLinkedList<number>();

doublyLinkedList.push(1);
doublyLinkedList.push(2);
doublyLinkedList.push(3);

console.log(doublyLinkedList.pop());
console.log(doublyLinkedList.shift());

doublyLinkedList.unshift(4);
console.log(doublyLinkedList.pop());