public class MyArrayList<E> {
    private int size;
    private E[] data;

    public MyArrayList() {
        data = (E[]) new Object[100]; // unchecked cast warning normal here
        size = 0;
    }

    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        for (int i = size - 1; i >= index; i--)
            data[i + 1] = data[i];
        data[index] = e;
        size++;
    }

    public boolean contains(Object e) {
        for (int i = 0; i < size; i++)
            if (e.equals(data[i]))
                return true;
        return false;
    }

    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        return data[index];
    }

    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        E e = data[index];
        for (int j = index; j < size - 1; j++)
            data[j] = data[j + 1];
        data[size - 1] = null;
        size--;
        return e;
    }

    public int size() {
        return size;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            result.append(data[i]);
            if (i < size - 1)
                result.append(", ");
        }
        result.append("]");
        return result.toString();
    }
}