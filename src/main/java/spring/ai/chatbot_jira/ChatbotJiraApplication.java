package spring.ai.chatbot_jira;

import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ChatbotJiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotJiraApplication.class, args);


		String text = "En un tranquilo pueblo escondido entre las montañas, donde el aire fresco susurraba secretos a través de los pinos y" +
				" el río serpenteaba como un hilo de plata bajo el cielo azul, vivía una anciana llamada Elara. " +
				"Elara era conocida por su sabiduría y su jardín mágico, un lugar donde las flores brillaban con colores nunca antes " +
				"vistos y las hierbas curativas crecían en abundancia. Cada tarde, los aldeanos se reunían en su jardín, buscando consejo" +
				" y remedios para sus males. Un día, un joven llamado Liam llegó al pueblo, buscando una cura para la extraña enfermedad" +
				" que afligía a su hermana. Elara, con su mirada profunda y su sonrisa amable, lo recibió en su jardín y " +
				"le mostró las plantas que podrían ayudar. Juntos, recolectaron las hierbas y prepararon una poción," +
				" mientras Elara le contaba historias de la naturaleza y sus poderes curativos. Liam, maravillado por la sabiduría " +
				"de Elara y la belleza del jardín, comprendió que la verdadera curación no solo venía de las plantas, sino también del" +
				" amor y la conexión con la naturaleza. Cuando regresó a su hogar con la poción," +
				" su hermana sanó rápidamente, y Liam supo que nunca olvidaría las lecciones aprendidas en el jardín mágico de Elara.";

		String text2 = "En el corazón de un antiguo bosque, donde los árboles centenarios susurraban historias al viento y la luz del sol se filtraba entre " +
				"las hojas creando un mosaico dorado, se encontraba una pequeña cabaña. Allí vivía un anciano llamado Einar, un sabio ermitaño conocido por" +
				" su profundo conocimiento de las plantas y los animales. Cada mañana, los habitantes de los pueblos cercanos acudían a su cabaña, buscando remedios " +
				"para sus enfermedades y consejos para sus problemas. Un día, una joven llamada Anya llegó al bosque, buscando una cura para la fiebre que consumía a " +
				"su madre. Einar, con su mirada serena y su voz suave, la guió a través del bosque, mostrándole las hierbas que podrían ayudar. Juntos, recolectaron las" +
				" plantas y prepararon un ungüento, mientras Einar le contaba leyendas de los espíritus del bosque y sus poderes curativos. Anya, asombrada por la sabiduría " +
				"de Einar y la magia del bosque, comprendió que la verdadera sanación no solo venía de las plantas, sino también de la conexión con la tierra y sus secretos. " +
				"Cuando regresó a su hogar con el ungüento, su madre sanó rápidamente, y Anya supo que nunca olvidaría las lecciones aprendidas en la cabaña de Einar";

		String text3 = "En el año 2347, la Tierra se encontraba al borde del colapso ecológico. Las megaciudades, otrora símbolos de progreso," +
				" ahora eran laberintos de acero y smog, donde la humanidad luchaba por sobrevivir. La Corporación OmniCorp, una entidad todopoderosa, " +
				"controlaba los recursos restantes y experimentaba con la manipulación genética para crear una raza superior. Un joven hacker llamado Kai, " +
				"que vivía en los bajos fondos de Neo-Tokio, descubrió un oscuro secreto: OmniCorp planeaba desatar un virus que eliminaría a la " +
				"población 'inferior'. Kai, junto a un grupo de rebeldes cibernéticos, se embarcó en una misión para exponer los crímenes de OmniCorp " +
				"y salvar a la humanidad de la extinción.";

		String text4 = "La pequeña ciudad de Harmony Creek era conocida por su anual concurso de pasteles, un evento que atraía a panaderos de todo el estado." +
				" Este año, la competencia estaba más reñida que nunca, con la llegada de la famosa chef francesa, Madame Colette. Entre los competidores " +
				"locales se encontraba Mildred McMillan, una anciana con un talento para los pasteles 'creativos' (léase: extraños). Mildred había decidido " +
				"participar con su última creación: un pastel de zanahoria con glaseado de pepinillos y cobertura de sardinas. Cuando el jurado probó su pastel" +
				", las reacciones fueron... variadas. Algunos se desmayaron, otros corrieron al baño y uno incluso intentó sobornar a Mildred para que se llevara " +
				"su pastel lejos. A pesar del caos, Mildred ganó el concurso, demostrando que en Harmony Creek, la creatividad culinaria no tenía límites";


		String text5 = "En el valle oculto, donde el río cantaba melodías ancestrales y las montañas se alzaban como guardianes de piedra bajo el cielo estrellado, " +
				"se encontraba un antiguo monasterio. Allí vivía un monje llamado Seraphim, un hombre sabio y bondadoso conocido por su profundo conocimiento de las hierbas " +
				"y los rituales curativos. Cada amanecer, los peregrinos de tierras lejanas llegaban al monasterio, buscando remedios para sus dolencias y consejos para " +
				"sus angustias. Un día, una joven llamada Lyra llegó al valle, buscando una cura para la ceguera que afligía a su padre. Seraphim, con su mirada compasiva y" +
				" su voz tranquila, la guió a través de los jardines del monasterio, mostrándole las plantas que podrían ayudar. Juntos, recolectaron las hierbas y prepararon " +
				"un elixir, mientras Seraphim le contaba parábolas de la luz y la oscuridad, y cómo ambas eran necesarias para la vida. Lyra, conmovida por la" +
				" sabiduría de Seraphim y la paz del monasterio, comprendió que la verdadera sanación no solo venía de las plantas, sino también de la aceptación y " +
				"la armonía con el mundo. " +
				"Cuando regresó a su hogar con el elixir, su padre recuperó la vista, y Lyra supo que nunca olvidaría" +
				" las lecciones aprendidas en el monasterio de Seraphim.";

/*
		Criteria<String, float[]> criteria =
				Criteria.builder()
						.setTypes(String.class, float[].class)
						.optModelUrls(
								"djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L6-v2")
						.optEngine("PyTorch")
						.optTranslatorFactory(new TextEmbeddingTranslatorFactory())
						.optProgress(new ProgressBar())
						.build();

		try (ZooModel<String, float[]> model = criteria.loadModel();
			 Predictor<String, float[]> predictor = model.newPredictor()) {
			float[] vector = predictor.predict(text5);
			String textVector = Arrays.toString(vector);
			//System.out.println("Embedding Generado es: " + textVector);

*/

		try{
			Connection conexion = conexionOracle("jdbc:oracle:thin:@//localhost:1521/FREE",  "SYS AS SYSDBA", "violador23");
			System.out.println(conexion);

			/*
			String titulo2 = "Error al cargar archivo";
			String descripcion2 = "Al intentar subir un archivo PDF, la aplicación se congela y se cierra. Esto ocurre con archivos de más de 5 MB.";
			String comentario2 = "He probado con diferentes archivos PDF y el problema persiste. También he intentado con otros formatos, pero solo ocurre con PDF";
			String label2 = "Carga de archivos";
			insertarDatos(conexion, titulo2, descripcion2, comentario2, label2);
			*/


			buscarTicketMasSimilar(conexion, "jan lukas hijo de puta");


		} catch (TranslateException | IOException | ModelNotFoundException | MalformedModelException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


	}
	public static double cosineSimilarity(float[] vec1, float[] vec2) {
		double dotProduct = 0.0;
		double normVec1 = 0.0;
		double normVec2 = 0.0;

		for (int i = 0; i < vec1.length; i++) {
			dotProduct += vec1[i] * vec2[i];
			normVec1 += Math.pow(vec1[i], 2);
			normVec2 += Math.pow(vec2[i], 2);
		}

		return dotProduct / (Math.sqrt(normVec1) * Math.sqrt(normVec2));
	}

	public static Connection conexionOracle(String url, String user, String password) throws SQLException, ClassNotFoundException {
		//asegurar que se cargue el driver de Oracle
		Class.forName("oracle.jdbc.OracleDriver");
		Connection connection = DriverManager.getConnection(url,user, password);
		return connection;
	}


	public static String generateEmbeding(String parametersConcat) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
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

	public static float[] generateEmbedingFloat(String parametersConcat) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
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
		return value;
	}

	public static void insertarDatos(Connection conn, String titulo, String descripcion, String comentario, String label) throws SQLException, TranslateException, ModelNotFoundException, MalformedModelException, IOException {
		String sql = "INSERT INTO TICKETS (TITULO_TICKET, DESCRIPCION_TICKET, COMENTARIO_TICKET, LABEL, ID_USUARIO, ID_ESTADO, EMBEDDING_NEW) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, titulo);
			stmt.setString(2, descripcion);
			stmt.setString(3, comentario);
			stmt.setString(4, label);
			stmt.setInt(5, 6);
			stmt.setInt(6, 9);

			String concat = titulo + descripcion + comentario + label;
			String embedding = generateEmbeding(concat);
			stmt.setObject(7, embedding);

			stmt.executeUpdate();
			System.out.println("✅ Ticket insertado con embeddings generados en Java");
		}
	}


	public static Map<Integer, float[]> obtenerEmbeddingsDesdeDB(Connection conn) throws SQLException {
		Map<Integer, float[]> embeddingsMap = new HashMap<>();

		String sql = "SELECT ID_TICKET, EMBEDDING_NEW FROM TICKETS";
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int ticketId = rs.getInt("ID_TICKET");
				String embeddingStr = rs.getString("EMBEDDING_NEW");

				// Convertir el String de la DB a un array float[]
				float[] embeddingArray = convertirStringAFloatArray(embeddingStr);
				embeddingsMap.put(ticketId, embeddingArray);
			}
		}
		return embeddingsMap;
	}

	public static float[] convertirStringAFloatArray(String embeddingStr) {
		String[] valores = embeddingStr.replace("[", "").replace("]", "").split(",");
		float[] embeddingArray = new float[valores.length];

		for (int i = 0; i < valores.length; i++) {
			embeddingArray[i] = Float.parseFloat(valores[i].trim());
		}
		return embeddingArray;
	}

	public static void buscarTicketMasSimilar(Connection conn, String consulta) throws Exception {
		// Obtener los embeddings desde la DB
		Map<Integer, float[]> embeddingsDB = obtenerEmbeddingsDesdeDB(conn);

		// Generar el embedding para la consulta
		float[] embeddingConsulta = generateEmbedingFloat(consulta);

		// Variable para el mejor resultado
		int mejorTicketId = -1;
		double mejorSimilitud = -1;

		// Comparar con todos los embeddings almacenados
		for (Map.Entry<Integer, float[]> entry : embeddingsDB.entrySet()) {
			int ticketId = entry.getKey();
			float[] embeddingDB = entry.getValue();

			// Calcular similitud coseno
			double similitud = cosineSimilarity(embeddingConsulta, embeddingDB);

			System.out.println("Similitud con Ticket ID " + ticketId + ": " + similitud);

			if (similitud > mejorSimilitud) {
				mejorSimilitud = similitud;
				mejorTicketId = ticketId;
			}
		}

		System.out.println("\nEl ticket más similar es: ID " + mejorTicketId + " con similitud " + mejorSimilitud);
	}

}
