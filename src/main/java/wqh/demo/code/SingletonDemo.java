package wqh.demo.code;

public class SingletonDemo {

	private SingletonDemo() {
		System.out.println("SingletonDemo类的单例对象被创建");
	}
	
	// 添加volatile关键字修饰，禁止指令重排，
	// 避免多线程环境下出现先指向一块空内存空间而未初始化此内存空间的情况
	private static volatile SingletonDemo instance;
	
	/**
	 * 使用双端检索机制，进行单例对象创建。
	 * 即synchronized前进行一次非空判断和synchronized后进行一次非空判断。
	 * synchronized前进行一次非空判断：满足此条件说明单例对象未创建，
	 * 则开始创建，第一步是加锁，只让一个线程进行创建。
	 * synchronized后进行一次非空判断：A线程和B线程都开始创建，同时竞争锁。
	 * A线程抢到锁，B线程阻塞。A开始创建单例对象，创建完毕后释放锁资源。
	 * B线程得到释放的锁资源，则又开始创建单例，第一步是判断单例对象是否为空，
	 * 发现非空则不会重复执行创建代码，直接返回单例对象。
	 * @return 
	 */
	public static SingletonDemo getInstance() {
		if(instance == null) {
			synchronized (SingletonDemo.class) {
				if(instance == null) {
					instance = new SingletonDemo();
				}
			}
		}
		return instance;
	}
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(()-> {
				SingletonDemo.getInstance();
			}).start();;
		}

	}

}
