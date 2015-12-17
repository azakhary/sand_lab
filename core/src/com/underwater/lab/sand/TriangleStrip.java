package com.underwater.lab.sand;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

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

        int attrCount = 8;
        float[] vertices = new float[(size+1)*(size+1)*attrCount];
        mesh = new Mesh(true, (size+1)*(size+1)*attrCount, 0,
                new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE));

        int vidx = 0;
        for(int row = 0; row < size; row++) {
            int col_start;
            int col_dir;
            int col_iter = 0;
            if(row % 2 == 0) {
                col_start = 0;
                col_dir = 1;
            } else {
                col_start = size;
                col_dir = -1;
            }

            for(int col = col_start; col_iter++ <= size; col+=col_dir) {
                vertices[vidx++] = col*side;
                vertices[vidx++] = 0;
                vertices[vidx++] = row*side;
                vertices[vidx++] = col*side;
                vertices[vidx++] = row*side;
                vertices[vidx++] = 0;
                vertices[vidx++] = 1;
                vertices[vidx++] = 0;

                vertices[vidx++] = col*side;
                vertices[vidx++] = 0;
                vertices[vidx++] = (row+1)*side;
                vertices[vidx++] = col*side;
                vertices[vidx++] = (row+1)*side;
                vertices[vidx++] = 0;
                vertices[vidx++] = 1;
                vertices[vidx++] = 0;
            }

            mesh.setVertices(vertices);

            meshPart.mesh = mesh;
            meshPart.primitiveType = GL20.GL_TRIANGLES;
        }
    }
}
