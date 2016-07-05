package game.entities.collisions;

import game.entities.Entity;

public interface CollisionState {

	// Interface para ser implementada pela classe referente a a��o ao colidir.
	// A sobrescrita do m�todo � o que trata a colisao para cada entidade
	// espec�fica.

	public void onCollision(Entity collider);
}
