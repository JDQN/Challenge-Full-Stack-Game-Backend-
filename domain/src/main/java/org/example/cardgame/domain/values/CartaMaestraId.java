package org.example.cardgame.domain.values;

import co.com.sofka.domain.generic.Identity;

/**
 * The type Carta maestra id.
 */
public class CartaMaestraId extends Identity {
    /**
     * Instantiates a new Carta maestra id.
     */
    public CartaMaestraId() {

    }

    private CartaMaestraId(String id) {
        super(id);
    }

    /**
     * Of carta maestra id.
     *
     * @param id the id
     * @return the carta maestra id
     */
    public static CartaMaestraId of(String id) {
        return new CartaMaestraId(id);
    }
}
