package com.example.parquepumalinapp.navBottom.InfoParque

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parquepumalinapp.R
import com.example.parquepumalinapp.databinding.FragmentInfoParqueBinding

class InfoParqueFragment : Fragment() {

    private var _binding: FragmentInfoParqueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoParqueBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvBienvenida.typeface = ResourcesCompat.getFont(requireContext(), R.font.amoitar_regular)

        binding.tvDescBienvenida.text = "Los bosques profundos de Pumalín descienden hasta los fiordos, originando uno de los bordes costeros más espectaculares del planeta. Cientos de cascadas se desprenden de los ventisqueros y caen sobre las paredes de granito, mientras los volcanes Michimahuida y Chaitén coronan el paisaje. El protagonista indiscutido es el amenazado Alerce: 25% del total de las especies remanentes del país se refugian en este Parque Nacional y sus casi 3 milenios relatan la historia sin tiempo de este prístino ecosistema de las provincias de Palena y Llanquihue."
        binding.imagenBienvenido.setImageResource(R.drawable.img_bienvenida_parque)

        val trails = listOf(
            DatosTrail(
                "Historia",
                "Douglas Tompkins caminó, en el año 1989, bajo los alerces milenarios que alberga el actual Parque Nacional Pumalín. En ese entonces, estas tierras patagónicas pertenecían a diversos estancieros que habían adquirido el territorio en los años 20’ y lo tenían abandonado o lo utilizaban para la ganadería y cosecha maderera.\n" +
                        "\n" +
                        "Douglas, junto a su pareja Kristine Tompkins, comprendieron la importancia de la biodiversidad local y desde el año 1991 comenzaron a adquirir miles de hectáreas con el fin de conservarlas y protegerlas, transformando el territorio en un Santuario de la Naturaleza en el año 2005.\n" +
                        "\n" +
                        "Durante muchos años, la Fundación Tompkins Conservation- hoy Rewilding Chile– trabajó para la adquisición de territorios en la Patagonia con el sueño de donarlos al Sistema de Parques Nacionales de Chile.\n" +
                        "\n" +
                        "A pesar de las enormes controversias y suspicacias generadas por este proyecto, en el año 2018, se realizó la donación mas grande de tierras de un privado a un Estado en la historia de la humanidad, entregando 403 mil hectáreas en diversas áreas de la Patagonia. Nace así el Parque Nacional Pumalín Douglas Tompkins.\n",
                listOf(R.drawable.img_camping, R.drawable.img_hito_alerce_2, R.drawable.img_cabanias)
            ),
            DatosTrail(
                "Flora",
                "Los bosques del Parque Nacional Pumalín Douglas Tompkins, presentan un alto nivel de endemismo y subespecies únicas entre las que destacan el Alerce, Luma, Tepa, Canelo, Tineo, Tiaca, Coigues, Ulmo, Olivo, Mañío, Notro, entre otros.",
                listOf(R.drawable.img_volcan_michimahuida_lejos, R.drawable.img_fondo_principal_volcan)
            ),
            DatosTrail(
                "Fauna",
                "No es inusual avistar en los fiordos Toninas, Cormoranes, Pingüinos, Garzas, Martín Pescador, mientras que los bosques son el reino de aves como Chucao, Hued- Hued y Rayadito, y hábitat de mamíferos más difíciles de ver como El Pudú, Zorro, Gato Colocolo, Puma y el marsupial chileno: el monito del monte.",
                listOf(R.drawable.img_hito_alerce_2, R.drawable.img_volcan_michimahuida_lejos, R.drawable.img_parque_dron, R.drawable.img_alerce_vertical, R.drawable.img_camping_lago_blanco)
            ),
            DatosTrail(
                "Cómo llegar por el sector Norte",
                "Si vas a llegar en vehículo desde Puerto Montt, debes viajar 49 km por la Ruta 7 hacia el sur hasta llegar a Caleta La Arena, donde un ferry te permitirá cruzar hacia Caleta Puelche.\n" +
                        "\n" +
                        "Recorre otros 60 km hacia el sur para llegar a Hornopirén. Desde allí, deberás tomar una barcaza de las empresas Transportes Austral o Somarco para cruzar hacia Leptepu,  en un viaje que tarda cerca de 3 horas y media y que se debe coordinar previamente.\n" +
                        "\n" +
                        "Ahora deberás viajar 10 km para tomar una segunda barcaza en el sector de Fiordo Largo. En 40 minutos llegarás  a Caleta Gonzalo, lugar donde reinicia la Carretera Austral y donde se encuentra la mayor parte de la infraestructura turística del Parque Nacional Pumalín Douglas Tompinks.\n" +
                        "\n" +
                        "Ten en cuenta que hay buses que salen desde Puerto Montt con destino hacia el parque.  Si estás haciendo el viaje desde el sur, debes llegar por la Ruta 7 hasta el sector de El Amarillo.\n" +
                        "\n" +
                        "\n" +
                        "- El horario de atención del Parque es de lunes a domingo de 08:30 a 17:30 hrs.\n" +
                        "- El ingreso al Parque y sus senderos es gratuito, se cancela solo uso de áreas de campings según la tarifa de cada temporada. Conaf administra los campings Cascada Escondida, Lago Negro, Lago Blanco, el Volcán, Grande y Ventisquero, funcionan por orden de llegada sin reserva." ,
                listOf(R.drawable.img_cabanias, R.drawable.img_fondo_principal_volcan)
            ),DatosTrail(
                "Cómo llegar por el sector Sur",
                        "Si vienes desde el sur, toma la Ruta 7 y dirígete hacia el Amarillo, ubicado a unos 120 kms. de La Junta.",
                listOf(R.drawable.img_entrada_el_amarillo)
            ),
            DatosTrail(
                "Restricciones",
                "-No uso del fuego\n" +
                        "-Llevar su basura\t\n" +
                        "-No cazar\n" +
                        "-No recolectar especies florales",
                listOf()
            )
            //Instructivo de como usar la app?????????
        )

        val adapter = AdaptadorDatosTrail(trails)
        binding.trailRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.trailRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}