package com.Vivern.Arpg.arpgfix;

/**
 * Нечистый приёмчик, чтобы хранить клиентские классы в static-полях и не ловить краш мода
 * при загрузке классов с такими полями на сервере.
 * By PurplePrint
 */
public abstract class AbstractClientFieldsContainer implements IFieldInit {
    public AbstractClientFieldsContainer() {
        this.initFields();
    }
}