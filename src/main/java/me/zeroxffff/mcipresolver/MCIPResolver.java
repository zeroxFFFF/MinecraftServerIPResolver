package me.zeroxffff.mcipresolver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.xbill.DNS.Record;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.TextParseException;


public class MCIPResolver {

	/*
	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>5.
	 */
	
	private static final String QUERY = "_minecraft._tcp.";

	
	public static List<?> getAdress(String ip) {
		
		try {
			Record[] records = new org.xbill.DNS.Lookup(QUERY+ip, 33).run();
			
	             SRVRecord srv = (SRVRecord) records[0];

	          String hostname = srv.getTarget().toString();
	        int port = srv.getPort();
	        
	        InetAddress address = InetAddress.getByName(hostname); 
	        return Arrays.asList(address.getHostAddress().toString().trim(), Integer.toString(port));
	        
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				    "Desculpe, mas não foi possível encontrar o endereço de ip. \nVerifique se você está digitando o endereço do servidor corretamente!",
				    "Minecraft IP Resolver",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}

        
        
	}
	
	public static void main(String[] args) throws TextParseException, UnknownHostException {
		System.out.println(getAdress("mc.gamerspvp.net").get(0));
	}

}
