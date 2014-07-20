/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

public enum SituacaoType {

    ANDAMENTO("Em andamento"),
    FECHADO("Fechado");
    private final String type;

    private SituacaoType(String type) {
        this.type = type;
    }

    private String type() {
        return this.type;
    }

    public String asString() {
        return this.type;
    }
}
