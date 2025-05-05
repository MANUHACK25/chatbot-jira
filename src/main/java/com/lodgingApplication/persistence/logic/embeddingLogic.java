package com.lodgingApplication.persistence.logic;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import com.lodgingApplication.persistence.TicketRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class embeddingLogic {
    private static double dotProduct = 0.0;
    private static double normVec1 = 0.0;
    private static double normVec2 = 0.0;
    private static TicketRepository ticketRepo = new TicketRepository();


    public static double cosineSimilarity(float[] vec1, float[] vec2) {
        for (int i = 0; i < vec1.length; i++) {
            dotProduct += vec1[i] * vec2[i];
            normVec1 += Math.pow(vec1[i], 2);
            normVec2 += Math.pow(vec2[i], 2);
        }

        return dotProduct / (Math.sqrt(normVec1) * Math.sqrt(normVec2));
    }


    /**genera Embedding que devuelve un String
     * TODO: sino sirve para otra cosa eliminarlo
     * */
    public static String generateEmbeding(String parametersConcat) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException, ModelNotFoundException, MalformedModelException, IOException {
        Criteria<String, float[]> criteria =
                Criteria.builder()
                        .setTypes(String.class, float[].class)
                        .optModelUrls(
                                "djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L6-v2")
                        .optEngine("PyTorch")
                        .optTranslatorFactory(new TextEmbeddingTranslatorFactory())
                        .optProgress(new ProgressBar())
                        .build();

        ZooModel<String, float[]> model = criteria.loadModel();
        Predictor<String, float[]> predictor = model.newPredictor();
        float[] value = predictor.predict(parametersConcat);
        String embedding = Arrays.toString(value);
        return embedding;
    }


    /**genera Embedding que devuelve un float[]
     * */
    public static float[] generateEmbedingFloat(String parametersConcat) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        Criteria<String, float[]> criteria =
                Criteria.builder()
                        .setTypes(String.class, float[].class)
                     //   .optModelUrls(
                      //          "djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L6-v2")
                        //        "djl://ai.djl.huggingface.pytorch/sentence-transformers/msmarco-MiniLM-L-12-v3")
                        //         "djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L12-v2")
                        //     "djl://ai.djl.huggingface.pytorch/sentence-transformers/multi-qa-MiniLM-L6-cos-v1")

                        ///multi-qa-MiniLM-L6-cos-v1
                        .optModelUrls(
                                   "djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L6-v2")
                        .optEngine("PyTorch")
                        .optTranslatorFactory(new TextEmbeddingTranslatorFactory())
                        .optProgress(new ProgressBar())
                        .build();
        ZooModel<String, float[]> model = criteria.loadModel();
        Predictor<String, float[]> predictor = model.newPredictor();
        float[] value = predictor.predict(parametersConcat);
        return value;
    }

    public static float[] convertirStringAFloatArray(String embeddingStr) {
        String[] valores = embeddingStr.replace("[", "").replace("]", "").split(",");
        float[] embeddingArray = new float[valores.length];

        for (int i = 0; i < valores.length; i++) {
            embeddingArray[i] = Float.parseFloat(valores[i].trim());
        }
        return embeddingArray;
    }





}
