package com.underwater.lab.sand;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by azakhary on 12/17/2015.
 */
public class TriangleStrip extends Renderable {

    private float size;

    private Mesh mesh;

    public void TriangleStrip() {
    }

    public void create(int size) {
        this.size = size;

        float side = 1f;

        int attrCount = 5;
        float[] vertices = new float[(size+1)*(size+1)*attrCount];
        short[] indices = new short[size*size*2*3];

         mesh = new Mesh(true, (size+1)*(size+1)*attrCount, size*size*2*3,
                         new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                         new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE));


        int vidx = 0;
        int iidx = 0;

        for(int row = 0; row <= size; row++) {
            for(int col = 0; col <= size; col++) {
                vertices[vidx++] = col*side;
                vertices[vidx++] = 0;
                vertices[vidx++] = row*side;
                vertices[vidx++] = row*side/(float)size;
                vertices[vidx++] = 1f-col*side/(float)size;
            }
        }

        for(short row = 0; row < size; row++) {
            for(short col = 0; col < size; col++) {
                indices[iidx++] = (short) (row * (size+1) + col);
                indices[iidx++] = (short) ((row + 1) * (size+1) + col);
                indices[iidx++] = (short) (row * (size+1) + col + 1);

                indices[iidx++] = (short) ((row + 1) * (size+1) + col);
                indices[iidx++] = (short) ((row + 1) * (size+1) + col + 1);
                indices[iidx++] = (short) (row * (size+1) + col + 1);
            }
        }

        mesh.setVertices(vertices);
        mesh.setIndices(indices);

        meshPart.mesh = mesh;
        meshPart.size = vertices.length;
        meshPart.primitiveType = GL20.GL_TRIANGLES;
        //meshPart.primitiveType = GL20.GL_LINE_STRIP;
    }
}
