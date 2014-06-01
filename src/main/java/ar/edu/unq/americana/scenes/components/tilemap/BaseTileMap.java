package ar.edu.unq.americana.scenes.components.tilemap;

import java.util.List;

import ar.edu.unq.americana.ia.pathfindier.Node;
import ar.edu.unq.americana.ia.pathfindier.TileMap;

public class BaseTileMap implements TileMap {

	private final Node[][] nodes;
	private final ITileMapScene scene;
	private Positionable target;

	public BaseTileMap(final ITileMapScene scene) {
		this.nodes = new Node[scene.rowsCount()][scene.columnsCount()];
		this.scene = scene;
	}

	private void generateNodes(final int rows, final int columns) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.nodes[i][j] = new Node(i, j, this.getHeristic(i, j));
			}

		}
	}

	public int getHeristic(final int row, final int column) {
		return Math.abs(row - this.target.getRow())
				+ Math.abs(column - this.target.getColumn());
	}

	@Override
	public void changeTarget(final Positionable target) {
		this.target = target;
		this.generateNodes(this.scene.rowsCount(), this.scene.columnsCount());
	}

	@Override
	public Node getNode(final int row, final int column) {
		final Node node = this.nodes[row][column];
		if (!this.scene.isAccessible(row, column)) {
			node.notAccesible();
		}
		return node;
	}

	@Override
	public List<Node> getAdjacents(final Node current) {
		return this.scene.adjacents(current);
	}

}
