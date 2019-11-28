/*
 * Copyright (C) 2016-2019 Alexandre Oliveira <alexandre.oliveira@viglet.com> 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.viglet.vecchio.onstartup.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.persistence.model.system.VecLocale;
import com.viglet.vecchio.persistence.repository.system.VecLocaleRepository;

@Component
public class VecLocaleOnStartup {
	@Autowired
	private VecLocaleRepository vecLocaleRepository;

	public void createDefaultRows() {

		if (vecLocaleRepository.findAll().isEmpty()) {

			vecLocaleRepository.save(new VecLocale("ar", "العربية - Arabic", "العربية - Árabe"));
			vecLocaleRepository.save(new VecLocale("bn", "বাংলা - Bengali", "বাংলা - Bengali"));
			vecLocaleRepository.save(new VecLocale("ca", "Català - Catalan (beta)", "Català - Catalão (beta)"));
			vecLocaleRepository.save(new VecLocale("cs", "Čeština - Czech", "Čeština - Tcheco"));
			vecLocaleRepository.save(new VecLocale("da", "Dansk - Danish", "Dansk - Dinamarquês"));
			vecLocaleRepository.save(new VecLocale("de", "Deutsch - German", "Deutsch - Alemão"));
			vecLocaleRepository.save(new VecLocale("el", "Ελληνικά - Greek", "Ελληνικά - Grego"));
			vecLocaleRepository.save(new VecLocale("en", "English", "English - Inglês"));
			vecLocaleRepository
					.save(new VecLocale("en-gb", "English UK - British English", "English UK - Inglês britânico"));
			vecLocaleRepository.save(new VecLocale("es", "Español - Spanish", "Español - Espanhol"));
			vecLocaleRepository.save(new VecLocale("eu", "Euskara - Basque", "Euskara - Basco"));
			vecLocaleRepository.save(new VecLocale("fa", "فارسی - Persian", "فارسی - Persa"));
			vecLocaleRepository.save(new VecLocale("fi", "Suomi - Finnish", "Suomi - Finlandês"));
			vecLocaleRepository.save(new VecLocale("fil", "Filipino", "Filipino"));
			vecLocaleRepository.save(new VecLocale("fr", "Français - French", "Français - Francês"));
			vecLocaleRepository.save(new VecLocale("ga", "Gaeilge - Irish", "Gaeilge - Irlandês"));
			vecLocaleRepository.save(new VecLocale("gl", "Galego - Galician", "Galego"));
			vecLocaleRepository.save(new VecLocale("gu", "ગુજરાતી - Gujarati", "ગુજરાતી - Guzerate"));
			vecLocaleRepository.save(new VecLocale("he", "עִבְרִית - Hebrew", "עִבְרִית - Hebraico"));
			vecLocaleRepository.save(new VecLocale("hi", "िन्दी - Hindi", "िन्दी - Híndi"));
			vecLocaleRepository.save(new VecLocale("hu", "Magyar - Hungarian", "Magyar - Húngaro"));
			vecLocaleRepository
					.save(new VecLocale("id", "Bahasa Indonesia - Indonesian", "Bahasa Indonesia - Indonésio"));
			vecLocaleRepository.save(new VecLocale("it", "Italiano - Italian", "Italiano"));
			vecLocaleRepository.save(new VecLocale("ja", "日本語 - Japanese", "日本語 - Japonês"));
			vecLocaleRepository.save(new VecLocale("kn", "ಕನ್ನಡ - Kannada", "ಕನ್ನಡ - Canarês"));
			vecLocaleRepository.save(new VecLocale("ko", "한국어- Korean", "한국어- Coreano"));
			vecLocaleRepository.save(new VecLocale("mr", "मराठी - Marathi", "मराठी - Marata"));
			vecLocaleRepository.save(new VecLocale("msa", "Bahasa Melayu - Malay", "Bahasa Melayu - Malaio"));
			vecLocaleRepository.save(new VecLocale("nl", "Nederlands - Dutch", "Nederlands - Holandês"));
			vecLocaleRepository.save(new VecLocale("no", "Norsk - Norwegian", "Norsk - Norueguês"));
			vecLocaleRepository.save(new VecLocale("pl", "Polski - Polish", "Polski - Polonês"));
			vecLocaleRepository.save(new VecLocale("pt-pt", "Português - Portuguese (Portugal)", "Português (Portugal)"));
			vecLocaleRepository.save(new VecLocale("pt-br", "Português - Portuguese (Brazil)", "Português (Brasil)"));
			vecLocaleRepository.save(new VecLocale("ro", "Română - Romanian", "Română - Romeno"));
			vecLocaleRepository.save(new VecLocale("ru", "Русский - Russian", "Русский - Russo"));
			vecLocaleRepository.save(new VecLocale("sv", "Svenska - Swedish", "Svenska - Sueco"));
			vecLocaleRepository.save(new VecLocale("ta", "தமிழ் - Tamil", "தமிழ் - Tâmil"));
			vecLocaleRepository.save(new VecLocale("th", "ภาษาไทย - Thai", "ภาษาไทย - Tailandês"));
			vecLocaleRepository.save(new VecLocale("tr", "Türkçe - Shkish", "Türkçe - Shco"));
			vecLocaleRepository.save(new VecLocale("uk", "Українська мова - Ukrainian", "Українська мова - Ucraniano"));
			vecLocaleRepository.save(new VecLocale("ur", "اردو - Urdu", "اردو - Urdu"));
			vecLocaleRepository.save(new VecLocale("vi", "Tiếng Việt - Vietnamese", "Tiếng Việt - Vietnamita"));
			vecLocaleRepository.save(new VecLocale("xx-lc", "LOLCATZ - Lolcat", "LOLCATZ - Lolcat"));
			vecLocaleRepository.save(new VecLocale("zh-cn", "简体中文 - Simplified Chinese", "简体中文 - Chinês simplificado"));
			vecLocaleRepository.save(new VecLocale("zh-tw", "简体中文 - Traditional Chinese", "繁體中文 - Chinês tradicional"));
		}
	}

}
