package com.lrdwhyt.dispace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TouchHandler implements GestureListener {

  private Camera camera;
  private float spaceSize;

  public TouchHandler(Camera camera, int spaceSize) {
    this.camera = camera;
    this.spaceSize = spaceSize;
  }

  @Override
  public boolean touchDown(float x, float y, int pointer, int button) {
    return false;
  }

  @Override
  public boolean tap(float x, float y, int count, int button) {
    Vector3 tapPosition = camera.unproject(new Vector3(x, y, 0));
    Vector3 adjustedTapPosition = tapPosition.sub(camera.position);
    float adjX = adjustedTapPosition.x;
    float adjY = adjustedTapPosition.y;
    if (adjX < 0) {
      adjX -= spaceSize / 2;
    } else {
      adjX += spaceSize / 2;
    }
    if (adjY < 0) {
      adjY -= spaceSize / 2;
    } else {
      adjY += spaceSize / 2;
    }
    System.out.println("Travelling to: " + (int) (adjX / spaceSize) + ", " + (int) (adjY / spaceSize));
    int cameraTranslateX = (int) spaceSize * (int) (adjX / spaceSize);
    int cameraTranslateY = (int) spaceSize * (int) (adjY / spaceSize);
    float step = Gdx.graphics.getDeltaTime() * 100 / (cameraTranslateX + cameraTranslateY);
    //animateTo(camera, cameraTranslateX, cameraTranslateY, step);
    camera.translate(new Vector3(cameraTranslateX, cameraTranslateY, 0));
    return false;
  }

  public void animateTo(Camera camera, int x, int y, float step) {
    if (camera.position.x != x && camera.position.y != y) {
      //camera.translate(new Vector3(x * step, y * step, 0));
      //animateTo(camera, x, y, step);
      Gdx.graphics.requestRendering();
    }
  }

  @Override
  public boolean longPress(float x, float y) {
    return false;
  }

  @Override
  public boolean fling(float velocityX, float velocityY, int button) {
    return false;
  }

  @Override
  public boolean pan(float x, float y, float deltaX, float deltaY) {
    return false;
  }

  @Override
  public boolean panStop(float x, float y, int pointer, int button) {
    return false;
  }

  @Override
  public boolean zoom(float initialDistance, float distance) {
    return false;
  }

  @Override
  public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
    return false;
  }

  @Override
  public void pinchStop() {

  }
}
