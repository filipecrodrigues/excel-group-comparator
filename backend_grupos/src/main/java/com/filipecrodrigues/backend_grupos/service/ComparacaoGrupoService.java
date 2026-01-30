package com.filipecrodrigues.backend_grupos.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.filipecrodrigues.backend_grupos.model.LinhaComparacao;

@Service
public class ComparacaoGrupoService {

    public Set<String> comparar(List<LinhaComparacao> linhas) {

        Set<String> ref = new HashSet<>();
        Set<String> comp = new HashSet<>();

        for (LinhaComparacao l : linhas) {
            if (l.getGrupoReferencia() != null)
                ref.add(l.getGrupoReferencia());

            if (l.getGrupoComparado() != null)
                comp.add(l.getGrupoComparado());
        }

        ref.removeAll(comp);
        return ref;
    }
}
