//  Network.swift
//  VUCHOBE 1.0
//  Created by Rovshan on 15/11/2019.
//  Copyright © 2019 Rovshan. All rights reserved.
//

import Foundation
import UIKit

class Network{
    
    static func getRequest(url: String, body: String) ->UIImage?{
        var image: UIImage?
        let url : NSString = (url + body) as NSString
        let urlStr : NSString = url.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)! as NSString
        let searchURL : URL = URL(string: urlStr as String)!
        let session = URLSession.shared
        session.dataTask(with: searchURL) { (data, response, error) in
            if let data = data {

                image = UIImage(data: data)
                print(data)
               
                do{
                    DispatchQueue.main.async {
                        
                    //let datan = Data(bytes: data, count: data.count)
                    //hf,  image = UIImage(data: data)!
                    //image = UIImage(data: data as Data) ?? UIImage(named: "man")!
                    }
                }
            }else{
            }
        }.resume()
        return image
    }
    
//    static func getData(from url: URL, completion: @escaping (Data?, URLResponse?, Error?) -> ()) {
//        URLSession.shared.dataTask(with: url, completionHandler: completion).resume()
//    }
//    
//    static func downloadImage(from url: URL) -> UIImage{
//        print("Download Started")
//        var myImage: UIImage
//        
//        Network.getData(from: url) { data, response, error in
//            guard let data = data, error == nil else { return }
//            print(response?.suggestedFilename ?? url.lastPathComponent)
//            print("Download Finished")
//            DispatchQueue.main.async() {
//               
//            
//            }
//        }
//      
//        
//        return myImage
//        
//    }
}
