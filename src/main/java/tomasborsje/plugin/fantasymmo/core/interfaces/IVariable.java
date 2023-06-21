package tomasborsje.plugin.fantasymmo.core.interfaces;

/**
 * !! Important !!
 * This interface indicates an item can vary for whatever reason.
 * E.G. A staff's damage increases based on the player's intelligence,
 * so we can't have just a static instance. This interface allows items to update themselves.
 */
public interface IVariable {
    public ICustomItem populate();
}
