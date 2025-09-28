package mod.acomit.fuckforgeobjmodelrender.test.block.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.acomit.fuckforgeobjmodelrender.Fuckforgeobjmodelrender;
import mod.acomit.fuckforgeobjmodelrender.core.mikktspace.MikkTSpaceContext;
import mod.acomit.fuckforgeobjmodelrender.core.mikktspace.MikktspaceTangentGenerator;
import mod.acomit.fuckforgeobjmodelrender.core.obj.*;
import mod.acomit.fuckforgeobjmodelrender.utils.TextureLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-09-27 13:41
 * @Description: TODO
 */
public class RenderCache {
    protected int VAO;

    protected int positionBufferObject;
    protected int normalsBufferObject;
    protected int uvBufferObject;
    protected int tangentBufferObject;

    protected int EBO;
    protected int indexCount;// 索引计数

    private int diffuseTextureId = -1;

    private final static ResourceLocation MODEL_LOC   = Fuckforgeobjmodelrender.prefix("model/test.obj");
    private final static ResourceLocation TEXTURE_LOC = Fuckforgeobjmodelrender.prefix("model/test.png");

    public void init(boolean isFlipY){
        try (InputStream stream = Minecraft.getInstance().getResourceManager()
                .getResource(MODEL_LOC)
                .orElseThrow(
                        () -> new IOException("Resource not found: " + MODEL_LOC)
                )
                .open()
        ) {

            Obj      obj   = ObjReader.read(stream);//读取
            ObjGroup group = obj.getGroup("base");//模型组
            if (group != null) {
                Obj groupObj = ObjUtils.groupToObj         (obj, group, null);//将组转换为obj模型
                groupObj     = ObjUtils.convertToRenderable(groupObj);//转化为可渲染的格式（模型三角形化，共用UV和法线的顶点拆分）

                IntBuffer   vertexIndices  = ObjData.getFaceVertexIndices(groupObj, 3);// 顶点索引
                FloatBuffer vertexPosition = ObjData.getVertices         (groupObj);// 顶点坐标
                FloatBuffer vertexUv       = ObjData.getTexCoords        (groupObj, 2, isFlipY);// UV
                FloatBuffer vertexNormals  = ObjData.getNormals          (groupObj);// 法线

                FloatBuffer vertexTangents = BufferUtils.createFloatBuffer(groupObj.getNumVertices() * 4);//切线
                //Mikk切线空间生成切线
                Obj finalGroupObj = groupObj;
                MikktspaceTangentGenerator.genTangSpaceDefault(new MikkTSpaceContext() {

                    @Override
                    public int getNumFaces() {
                        return finalGroupObj.getNumFaces();
                    }

                    @Override
                    public int getNumVerticesOfFace(int face) {
                        return 3;
                    }

                    @Override
                    public void getPosition(float[] out, int face, int vert) {
                        int idx = vertexIndices.get(face * 3 + vert) * 3;
                        vertexPosition.position(idx);
                        out[0] = vertexPosition.get();
                        out[1] = vertexPosition.get();
                        out[2] = vertexPosition.get();
                    }

                    @Override
                    public void getNormal(float[] out, int face, int vert) {
                        int idx = vertexIndices.get(face * 3 + vert) * 3;
                        vertexNormals.position(idx);
                        out[0] = vertexNormals.get();
                        out[1] = vertexNormals.get();
                        out[2] = vertexNormals.get();
                    }

                    @Override
                    public void getTexCoord(float[] out, int face, int vert) {
                        int idx = vertexIndices.get(face * 3 + vert) * 2;
                        vertexUv.position(idx);
                        out[0] = vertexUv.get();
                        out[1] = vertexUv.get();
                    }

                    @Override
                    public void setTSpaceBasic(float[] tangent, float sign, int face, int vert) {
                        int idx = vertexIndices.get(face * 3 + vert) * 4;
                        vertexTangents.position(idx);
                        vertexTangents.put(tangent[0]);
                        vertexTangents.put(tangent[1]);
                        vertexTangents.put(tangent[2]);
                        vertexTangents.put(sign);
                    }

                    @Override
                    public void setTSpace(float[] tangent, float[] biTangent, float magS, float magT, boolean isOrientationPreserving, int face, int vert) {
                        // 不需要实现
                    }
                });

                vertexIndices .rewind();
                vertexPosition.rewind();
                vertexUv      .rewind();
                vertexNormals .rewind();
                vertexTangents.rewind();

                indexCount = vertexIndices.capacity();

                VAO = GL30.glGenVertexArrays();
                GL30.glBindVertexArray(VAO);

                positionBufferObject = GL30.glGenBuffers();
                GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, positionBufferObject);
                GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexPosition, GL30.GL_STATIC_DRAW);
                GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
                GL30.glEnableVertexAttribArray(0);

                uvBufferObject = GL30.glGenBuffers();
                GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, uvBufferObject);
                GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexUv, GL30.GL_STATIC_DRAW);
                GL30.glVertexAttribPointer(2, 2, GL30.GL_FLOAT, false, 0, 0);
                GL30.glEnableVertexAttribArray(2);

                normalsBufferObject = GL30.glGenBuffers();
                GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, normalsBufferObject);
                GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexNormals, GL30.GL_STATIC_DRAW);
                GL30.glVertexAttribPointer(5, 3, GL30.GL_FLOAT, false, 0, 0);
                GL30.glEnableVertexAttribArray(5);

                tangentBufferObject = GL30.glGenBuffers();
                GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, tangentBufferObject);
                GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexTangents, GL30.GL_STATIC_DRAW);
                GL20.glVertexAttribPointer(9, 4, GL11.GL_FLOAT, false, 0, 0); // 使用location=3
                GL20.glEnableVertexAttribArray(9);

                GL30.glVertexAttrib2f(8, 0.5f, 0.5f);  // MID

                // 颜色，light，overlay渲染时传，这里不传

                EBO = GL30.glGenBuffers();
                GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, EBO);
                GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, vertexIndices, GL30.GL_STATIC_DRAW);


                GL30.glBindVertexArray(0);
                GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
                GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);

            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load OBJ model: " + MODEL_LOC, e);
        }
    }

    public void render(PoseStack poseStack, int light, int overlay){
        ShaderInstance shader = GameRenderer.getRendertypeEntityTranslucentCullShader();
        if (diffuseTextureId == -1) {
            diffuseTextureId = TextureLoader.loadTexture(TEXTURE_LOC);
        }
        RenderSystem.setShaderTexture(0, diffuseTextureId);
        Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
        for (int i = 0; i < 12; ++i) {
            int j = RenderSystem.getShaderTexture(i);
            shader.setSampler("Sampler" + i, j);
        }
        if (shader.MODEL_VIEW_MATRIX != null) {
            shader.MODEL_VIEW_MATRIX.set(RenderSystem.getModelViewMatrix().mul(poseStack.last().pose(),new Matrix4f()));
        }
        if (shader.PROJECTION_MATRIX != null) {
            shader.PROJECTION_MATRIX.set(RenderSystem.getProjectionMatrix());
        }
        if (shader.INVERSE_VIEW_ROTATION_MATRIX != null) {
            shader.INVERSE_VIEW_ROTATION_MATRIX.set(RenderSystem.getInverseViewRotationMatrix());
        }
        if (shader.COLOR_MODULATOR != null) {
            shader.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
        }
        if (shader.FOG_START != null) {
            shader.FOG_START.set(RenderSystem.getShaderFogStart());
        }
        if (shader.FOG_END != null) {
            shader.FOG_END.set(RenderSystem.getShaderFogEnd());
        }
        if (shader.FOG_COLOR != null) {
            shader.FOG_COLOR.set(RenderSystem.getShaderFogColor());
        }
        if (shader.FOG_SHAPE != null) {
            shader.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
        }
        if (shader.TEXTURE_MATRIX != null) {
            shader.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
        }
        if (shader.GAME_TIME != null) {
            shader.GAME_TIME.set(RenderSystem.getShaderGameTime());
        }
        if (shader.SCREEN_SIZE != null) {
            Window window = Minecraft.getInstance().getWindow();
            shader.SCREEN_SIZE.set((float)window.getWidth(), (float)window.getHeight());
        }

        // 法线在应用modelview矩阵之前就被应用到光照了，所以对Light Direction下手
        Matrix4f inverseModelMatrix = new Matrix4f();
        poseStack.last().pose().invert(inverseModelMatrix);
        inverseModelMatrix.transpose();
        if (shader.LIGHT0_DIRECTION != null) {
            Vector3f transformedLightDir = new Vector3f(RenderSystem.shaderLightDirections[0]);
            inverseModelMatrix.transformDirection(transformedLightDir);
            transformedLightDir.normalize();
            shader.LIGHT0_DIRECTION.set(transformedLightDir);
        }
        if (shader.LIGHT1_DIRECTION != null) {
            Vector3f transformedLightDir = new Vector3f(RenderSystem.shaderLightDirections[1]);
            inverseModelMatrix.transformDirection(transformedLightDir);
            transformedLightDir.normalize();
            shader.LIGHT1_DIRECTION.set(transformedLightDir);
        }
        shader.apply();
        GL30.glBindVertexArray(VAO);

        // 颜色
        GL30.glVertexAttrib4f(1, 1.0f, 1.0f, 1.0f, 1.0f);  // RGBA(1,1,1,1)

        GL30.glVertexAttribI2i(3, overlay & '\uffff', overlay >> 16 & '\uffff');
        GL30.glVertexAttribI2i(4, light & '\uffff', light >> 16 & '\uffff');

        GL30.glDrawElements(
                GL30.GL_TRIANGLES,
                indexCount,
                GL30.GL_UNSIGNED_INT,
                0
        );

        GL30.glVertexAttribI2i(3, 0, 0);
        GL30.glVertexAttribI2i(4, 0, 0);

        GL30.glBindVertexArray(0);
        shader.clear();
    }
}
