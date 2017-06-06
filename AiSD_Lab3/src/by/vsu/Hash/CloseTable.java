package by.vsu.Hash;

import by.vsu.TableOverflowException;

//���-������� � �������� ����������, ���-������� h(x)= x mod SIZE;
public class CloseTable<V>
{
	private CloseItem [] items;
	
	public CloseTable(int size)
	{
		this.items = new CloseItem[size];
	}
	
	public int getSize()
	{
		return items.length;
	}
	
	//�������� item �� ������� h(x),���� �� �� ������, ����� �������� ������� ������. 
	//���� �������� ����� �������, � ����� �� �������, ��������� � ������.
	public void put(int key, V value) throws TableOverflowException 
	{
		int size = this.getSize();
		int pos = key%size;
		int quantityUncheckedPositions = size;
		while(quantityUncheckedPositions != 0)
		{
			
			if(items[pos%size] == null)
			{
			     items[pos%size] = new CloseItem<V>(key, value);
			     return;
			}
			pos++;
			quantityUncheckedPositions--;			
		}
		throw new TableOverflowException();
	}
	
	//���� �������� �� �����, �� ��� ��� ���� �� �������� null
	public V get(int key)
	{
		int pos = key%this.getSize();
		int quantityUncheckedPositions = this.getSize();
		while(quantityUncheckedPositions != 0 && items[pos] != null)
		{
			if(items[pos].getKey() == key)
			{
				return (V)items[pos].getValue();
			}
			pos++;
			pos %= this.getSize();
			quantityUncheckedPositions--;
		}
		return null;
	}
	
	//�������, � ����� ������� �������� �� �����.
	//��-�� �������� ����� ������������ ����, ������� ������� �������� ���������, ������ �� �� ���� ����� item'��
	public void pop(int key)
	{
		int size = this.getSize();
		int pos = key%size;
		int quantityUncheckedPositions = size; //������� ����� ��������������, ��� ���� ����� �� �� ��������� �� ������� �����
		int deletePosition = 0;
		boolean isDelete = false;
		while(quantityUncheckedPositions != 0)
		{			
			if(items[pos%size] != null)
			{
				if(items[pos%size].getKey() == key)//������� ��������, � ���������� ������� �� ������, ���� ������������� ����
				{
					items[pos%size] = null;
					deletePosition = pos;
					isDelete = true;
					pos++;
					quantityUncheckedPositions--;
					break;
				}
			}
			else //�������� ����� ���, �������� �� ����������
			{
				break;
			}
			pos++;
			quantityUncheckedPositions--;
		}
		quantityUncheckedPositions = size;
		
		while(isDelete)//���� ��������� ��������, �������� ����, ������� ����� ������������
		{	
			
			if(items[pos%size] != null)
			{
				if(items[pos%size].getKey()%size != pos%size)
				{
					if((pos - deletePosition) > (pos - items[pos%size].getKey() % size))
					//�������� �� ��� ������, ���� ������ ������� ����� �����, ��� �������� ������� item'a
					{
						pos++;
						continue;
					}
					items[deletePosition%size] = items[pos%size];
					//items[pos%size].setPos(deletePosition);
					items[pos%size] = null;
					deletePosition = pos;
				}
			}else
			{
				break;
			}
			pos++;
			quantityUncheckedPositions--;
		}
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder bodyBuilder = new StringBuilder();
		int size = items.length;
		bodyBuilder.append("������: " + size + "\n\n");
		for(int i = 0; i < size; i++)
		{
			if(this.items[i] != null)
			{
				bodyBuilder.append(this.items[i]);
				bodyBuilder.append('\n');
			}
		}
		return bodyBuilder.toString();
	}
}
