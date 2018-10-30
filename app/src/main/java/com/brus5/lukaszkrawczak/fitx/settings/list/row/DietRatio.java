package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class Somatotype extends MyRow
{
    public Somatotype(Context context, MySettingsList mySettingsList, String value)
    {
        super(mySettingsList);

        String name = context.getString(R.string.somatotype);
        String descriptionShort = context.getString(R.string.your_body_type);
        String dbName = "user_somatotype";
        String descriptionLong = "Somatotyp, to typ budowy ciała, określa całościowe proporcje ciała. Jako regułę wyróżnia się trzy somatotypy - ektomorfik, mezomorfik i endomorfik. Każdy z nich charakteryzuje się inną budową. Wybranie jednej z tych grup pozwoli na trafniejsze wyliczanie kalorii.\n" +
                "\n" +
                "+ 900 kcal - jeżeli masz niski poziom tkanki tłuszczowej, mało mięśni, dość szczupłe ramiona, płaską klatkę piersiową.\n" +
                "\n" +
                "+ 400 kcal - jeżeli masz niski poziom tkanki tłuszczowej, szerokie barki, wąską talię, widoczne mięśnie.\n" +
                "\n" +
                "+ 200 kcal - jeżeli masz wysoki poziom tkanki tłuszczowej, łatwo przybierasz na masie mięśniowej oraz tłuszczowej.\n" +
                "\n" +
                "Nie jesteśmy podzieleni tylko na trzy kategorie. Jeżeli wydaje Ci się, że przynależysz do jednej jak i do drugiej grupy to możesz wpisać wartość pośrednią. \n" +
                "Przykład: Możesz posiadać cechy endomorficzne jak i mezomorficzne. Wtedy sugerowana wartość pośrednia wynosiłaby +300 kcal. Również możesz posiadać cechy ektomorficzne jak i mezomorficzne to Twoja wartość wynosiłaby między 400 a 900 kcal.";

        super.name = name;
        super.value = value;
        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 1;
        super.descriptionLong = descriptionLong;

        setData();
    }
}
