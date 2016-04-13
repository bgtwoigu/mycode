package com.oneplus.camera.test;

import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.util.SparseArray;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MonkeyTouch {
	/**
	 * �����갴ť
	 * 
	 * @param x
	 * @param y
	 */
	public void clickOnScreen(float x, float y) {
		long downTime = SystemClock.uptimeMillis();
		SparseArray<MotionEvent.PointerCoords> mPointers = new SparseArray<MotionEvent.PointerCoords>();
		MotionEvent.PointerCoords c = new MotionEvent.PointerCoords();
		c.x = x;
		c.y = y;
		c.pressure = 0;
		c.size = 0;
		mPointers.append(0, c);

		int pointerCount = mPointers.size();
		int[] pointerIds = new int[pointerCount];
		MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[pointerCount];
		for (int i = 0; i < pointerCount; i++) {
			pointerIds[i] = mPointers.keyAt(i);
			pointerCoords[i] = mPointers.valueAt(i);
		}
		int mSource = InputDevice.SOURCE_TOUCHSCREEN;

		MotionEvent ev = MotionEvent.obtain(downTime,
				SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN,
				pointerCount, pointerIds, pointerCoords, 0, 1, 1, 0, 1,
				mSource, 1);

		InputManager.getInstance().injectInputEvent(ev,
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MotionEvent ev1 = MotionEvent.obtain(downTime,
				SystemClock.uptimeMillis(), MotionEvent.ACTION_UP,
				pointerCount, pointerIds, pointerCoords, 0, 1, 1, 0, 1,
				mSource, 1);

		InputManager.getInstance().injectInputEvent(ev1,
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

	}
	
	
	/**
	 * �����갴ť
	 * 
	 * @param x
	 * @param y
	 */
	public void clickLongOnScreen(float x, float y) {
		long downTime = SystemClock.uptimeMillis();
		SparseArray<MotionEvent.PointerCoords> mPointers = new SparseArray<MotionEvent.PointerCoords>();
		MotionEvent.PointerCoords c = new MotionEvent.PointerCoords();
		c.x = x;
		c.y = y;
		c.pressure = 0;
		c.size = 0;
		mPointers.append(0, c);

		int pointerCount = mPointers.size();
		int[] pointerIds = new int[pointerCount];
		MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[pointerCount];
		for (int i = 0; i < pointerCount; i++) {
			pointerIds[i] = mPointers.keyAt(i);
			pointerCoords[i] = mPointers.valueAt(i);
		}
		int mSource = InputDevice.SOURCE_TOUCHSCREEN;

		MotionEvent ev = MotionEvent.obtain(downTime,
				SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN,
				pointerCount, pointerIds, pointerCoords, 0, 1, 1, 0, 1,
				mSource, 1);

		InputManager.getInstance().injectInputEvent(ev,
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MotionEvent ev1 = MotionEvent.obtain(downTime,
				SystemClock.uptimeMillis(), MotionEvent.ACTION_UP,
				pointerCount, pointerIds, pointerCoords, 0, 1, 1, 0, 1,
				mSource, 1);

		InputManager.getInstance().injectInputEvent(ev1,
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

	}
	
	
	

	public void simulateKeystroke(int KeyCode) {
		// doInjectKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyCode));
		// doInjectKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyCode));
		InputManager.getInstance().injectInputEvent(
				new KeyEvent(KeyEvent.ACTION_DOWN, KeyCode),
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

		InputManager.getInstance().injectInputEvent(
				new KeyEvent(KeyEvent.ACTION_UP, KeyCode),
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

	}

	private void doInjectKeyEvent(MotionEvent mEvent) {
		try {
			// (IWindowManager.Stub.asInterface(ServiceManager.getService("window"))).injectPointerEvent(mEvent,
			// false);
			InputManager.getInstance().injectInputEvent(mEvent,
					InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

			// (IWindowManager.Stub.asInterface(ServiceManager.getService("window")))
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendKey(int mKeyCode) {

		long eventTime = SystemClock.uptimeMillis();
		long downTime = SystemClock.uptimeMillis();

		KeyEvent keyEvent = new KeyEvent(downTime, SystemClock.uptimeMillis(),
				KeyEvent.ACTION_DOWN, mKeyCode, 0, 0,
				KeyCharacterMap.VIRTUAL_KEYBOARD, 0, KeyEvent.FLAG_FROM_SYSTEM,
				InputDevice.SOURCE_KEYBOARD);

		InputManager.getInstance().injectInputEvent(keyEvent,
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);
		
		KeyEvent keyEvent1 = new KeyEvent(downTime, SystemClock.uptimeMillis(),
				KeyEvent.ACTION_UP, mKeyCode, 0, 0,
				KeyCharacterMap.VIRTUAL_KEYBOARD, 0, KeyEvent.FLAG_FROM_SYSTEM,
				InputDevice.SOURCE_KEYBOARD);

		InputManager.getInstance().injectInputEvent(keyEvent1,
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);
	}

	public MotionEvent getMontionEvent(float x, float y, int action) {
		long downTime = SystemClock.uptimeMillis();
		SparseArray<MotionEvent.PointerCoords> mPointers = new SparseArray<MotionEvent.PointerCoords>();
		MotionEvent.PointerCoords c = new MotionEvent.PointerCoords();
		c.x = x;
		c.y = y;
		c.pressure = 1;
		c.size = 5;
		mPointers.append(0, c);

		int pointerCount = mPointers.size();
		int[] pointerIds = new int[pointerCount];
		MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[pointerCount];
		for (int i = 0; i < pointerCount; i++) {
			pointerIds[i] = mPointers.keyAt(i);
			pointerCoords[i] = mPointers.valueAt(i);
		}
		int mSource = InputDevice.SOURCE_TOUCHSCREEN;

		MotionEvent ev = MotionEvent.obtain(downTime,
				SystemClock.uptimeMillis(), action, pointerCount, pointerIds,
				pointerCoords, 0, 1, 1, 0, 1, mSource, 1);
		return ev;
	}

	public void drag(float fromX, float fromY, float toX, float toY,
			int stepCount) {
		MotionEvent event = getMontionEvent(fromX, fromY,
				MotionEvent.ACTION_DOWN);

		InputManager.getInstance().injectInputEvent(event,
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

		float y = fromY;
		float x = fromX;

		float yStep = (toY - fromY) / stepCount;
		float xStep = (toX - fromX) / stepCount;

		 //waitForIdleSync();

		for (int i = 0; i < stepCount; i++) {
			y += yStep;
			x += xStep;
			event = getMontionEvent(x, y, MotionEvent.ACTION_MOVE);

			InputManager.getInstance().injectInputEvent(event,
					InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);
		}

		event = getMontionEvent(toX, toY, MotionEvent.ACTION_UP);

		InputManager.getInstance().injectInputEvent(event,
				InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);

	}

}
