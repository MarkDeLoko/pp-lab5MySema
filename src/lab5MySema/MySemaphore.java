package lab5MySema;

import java.util.concurrent.locks.ReentrantLock;

class MySemaphore {
	private static int maxPermits;
	private static int curPermit;
	private final static ReentrantLock lock = new ReentrantLock();

	public MySemaphore(int maxPermits) {
		this.maxPermits = maxPermits;
	}

	public static void enter() {
		lock.lock();
		{
			curPermit++;
			if (curPermit > maxPermits) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static void leave() {
		{
			curPermit--;
			lock.notify();
		}
	}

	public static int availablePermits() {
		return (maxPermits - curPermit);
	}
}

class MyATMThread extends Thread {

	String name = "";

	MyATMThread(String name) {
		this.name = name;
	}

	public void run() {

		try {
			System.out.println(name + " : Пытается занять кабинку");
			System.out.println(name + " : Узнал, что доступно " + MySemaphore.availablePermits() + " кабинок");
			MySemaphore.enter();
			System.out.println(name + " : Занял кабинку");

			try {
				System.out.println(name + " : Выполняет операцию" + ", на данный момент доступно : "
						+ MySemaphore.availablePermits() + " кабинок");
				Thread.sleep(10000);
			}

			finally {

				System.out.println(name + " : Освобождает кабинку");
				MySemaphore.leave();
				System.out.println(name + " : Узнал, что после его ухода стало доступно "
						+ MySemaphore.availablePermits() + " кабинок");

			}

		} catch (InterruptedException e) {

			e.printStackTrace();

		}

	}

	public static void main(String[] args) {
		MySemaphore MySemaphore = new MySemaphore(5);
		System.out.println("Доступные кабинки : " + MySemaphore.availablePermits());

		MyATMThread t1 = new MyATMThread("Наталья");
		t1.start();

		MyATMThread t2 = new MyATMThread("Александр");
		t2.start();

		MyATMThread t3 = new MyATMThread("Варфаламей");
		t3.start();

		MyATMThread t4 = new MyATMThread("Дмитрий");
		t4.start();

		MyATMThread t5 = new MyATMThread("Денчик");
		t5.start();

		MyATMThread t6 = new MyATMThread("Фёдор");
		t6.start();

		MyATMThread t7 = new MyATMThread("Екатерина");
		t7.start();

		MyATMThread t8 = new MyATMThread("Адольф");
		t8.start();

		MyATMThread t9 = new MyATMThread("Максим");
		t9.start();

		MyATMThread t10 = new MyATMThread("Елена");
		t10.start();
	}
}
