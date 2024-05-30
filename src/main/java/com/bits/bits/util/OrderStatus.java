package com.bits.bits.util;

public enum OrderStatus {
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento!"),
    PAGAMENTO_REJEITADO("Pagamento Rejeitado!"),
    PAGAMENTO_COM_SUCESSO("Pagamento com sucesso!"),
    AGUARDANDO_RETIRADA("Aguardando Retirada"),
    EM_TRANSITO("Em Trânsito!"),
    ENTREGUE("Entregue!");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
