package com.example.instagram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.instagram.R;
import com.example.instagram.fragments.CustomARFragment;
import com.google.ar.core.AugmentedFace;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.util.Collection;

public class FilterActivity extends AppCompatActivity {

    private ModelRenderable modelRenderable;
    private Texture texture;
    private boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        CustomARFragment customARFragment = (CustomARFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        ModelRenderable.builder().setSource(this, R.raw.fox_face)
                .build()
                .thenAccept(renderable -> {
                    modelRenderable = renderable;
                    modelRenderable.setShadowCaster(false);
                    modelRenderable.setShadowReceiver(false);
                });

        Texture.builder()
                .setSource(this, R.drawable.fox_face_mesh_texture)
                .build()
                .thenAccept(texture -> this.texture = texture);

        customARFragment.getArSceneView().setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        customARFragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                if (modelRenderable == null || texture == null)
                    return;
                Frame frame = customARFragment.getArSceneView().getArFrame();

                Collection<AugmentedFace> augmentedFaces = frame.getUpdatedTrackables(AugmentedFace.class);

                for (AugmentedFace augmentedFace: augmentedFaces) {
                    if (isAdded)
                        return;
                    AugmentedFaceNode augmentedFaceNode = new AugmentedFaceNode(augmentedFace);
                    augmentedFaceNode.setParent(customARFragment.getArSceneView().getScene());
                    augmentedFaceNode.setFaceRegionsRenderable(modelRenderable);
                    augmentedFaceNode.setFaceMeshTexture(texture);
                    isAdded = true;
                }
            }
        });






    }
}