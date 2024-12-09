package com.PersonalizaTuViaje.PersonalizaTuViaje.Service.impl;

import com.PersonalizaTuViaje.PersonalizaTuViaje.Firebase.FirebaseInitializer;
import com.PersonalizaTuViaje.PersonalizaTuViaje.LogicaDeNegocio.Usuario;
import com.PersonalizaTuViaje.PersonalizaTuViaje.Service.UserService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserSerivceImpl implements UserService {

    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<Usuario> ListaUsuarios() {
        List<Usuario> response=new ArrayList<>();
        Usuario usuario;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc:querySnapshotApiFuture.get().getDocuments()){
                usuario=doc.toObject(Usuario.class);
                usuario.setId(doc.getId());
                response.add(usuario);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Usuario ObtenerUsuario(String id) throws Exception {
        ApiFuture<DocumentSnapshot> future = getCollection().document(id).get();
        DocumentSnapshot document = future.get();

        if (document.exists()){
            return document.toObject(Usuario.class);
        }
        else {
            return null;
        }

    }

    @Override
    public Boolean NuevoUsuario(Usuario usuario) {
        Map<String, Object> docData = getObjectMap(usuario);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(usuario.getId()).create(docData);

        try {
            if (null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (InterruptedException | ExecutionException e){
            return Boolean.FALSE;
        }
    }



    @Override
    public Boolean EditarUsuario(String id, Usuario usuario) {
        Map<String, Object> docData = getObjectMap(usuario);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).set(docData);

        try {
            if (null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (InterruptedException | ExecutionException e){
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean PatchUsuario(String id, Map<String, Object> updates){
        CollectionReference collection = getCollection();
        DocumentReference docRef = collection.document(id);
        ApiFuture<WriteResult> writeResultApiFuture = docRef.update(updates);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }


    @Override
    public Boolean EliminarUsuario(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).delete();
        try {
            if (null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (InterruptedException | ExecutionException e){
            return Boolean.FALSE;
        }
    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("Users");
    }

    private Map<String, Object> getObjectMap(Usuario usuario) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("email", usuario.getEmail());
        docData.put("nombreUsuario", usuario.getNombreUsuario());
        docData.put("genero", usuario.getGenero());
        docData.put("edad", usuario.getEdad());
        docData.put("presupuesto", usuario.getPresupuesto());
        docData.put("Viajes", usuario.getListaViajes());
        return docData;
    }

}
