package model;
/**
 * 
 * @author linxi
 *因为我实在没想好到底应该交什么名字，，所以就随手起的哈哈哈
 */

import java.io.File;
import java.util.LinkedList;
import demo.BmpController;
import demo.Imgs2Pdf;

public class Unit {
	public static final int THREAD_NUM = 3;
	private int count;
	private String path;
	private LinkedList<Integer> task;
	private int completed;
	private String name;
	private static volatile LinkedList<Unit> ul = new LinkedList<>();
	private static boolean hasStartStart = false;

	public synchronized static void UnitMaker(int count, String path, String name) {
		Unit unit = new Unit(count, path, name);
		ul.add(unit);
		if (!hasStartStart) {
			start();// 只能从UnitMaker中启动一次，不然cpu会爆炸哈哈
			hasStartStart = true;
		}
	}

	private static void start() {
		if (!ul.isEmpty()) {
			Unit unit = ul.poll();
			for (int i = 0; i < THREAD_NUM; i++) {
				new Thread(() -> {
					Unit local_unit = unit;
					boolean flag = false;
					while (true) {
						int task = local_unit.getTask(flag);
						if (task != -1) {
							System.out.println("正在转色 [" + unit.getPath() + "] 第" + task + "张图片");
							try {
								BmpController.reverseBmpColor(unit.getPath() + "/" + task + "_temp.bmp",
										unit.getPath() + "/" + task + ".bmp");
							} catch (Exception e) {
								e.printStackTrace();
							}
							new File(unit.getPath() + "/" + task + "_temp.bmp").delete();// 删除原图
						} else {
							break;
						}
						flag = true;
					}
				}).start();
			}
		} else {
			System.out.println("所有任务处理完毕!");
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private Unit(int count, String path, String name) {
		super();
		this.name = name;
		this.count = count;
		this.path = path;
		task = new LinkedList<>();
		for (int i = 0; i < count; i++) {
			task.add(i);
		}
		completed = 0;
	}

	/**
	 * 
	 * @param flag
	 *            是否完成上一任务，如果没有上一任务则为false
	 * @return 一个count，如果为-1则为没有待分配任务，自动销毁当前线程
	 */
	public synchronized int getTask(boolean flag) {
		if (flag) {
			completed++;// 已完成任务数量
		}
		if (task.size() != 0) {
			return task.poll();
		} else if (completed == count) {
			try {
				Imgs2Pdf.createPdf(count, path, name + "_反色");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				for (int i = 0; i < count; i++) {
					new File(path + "/" + i + ".bmp").delete();
				}
				System.out.println("删除" + path + "中的临时图片文件完成");
				start();// 开始Unit队列中的下一个任务
			}
		}
		return -1;
	}

}
