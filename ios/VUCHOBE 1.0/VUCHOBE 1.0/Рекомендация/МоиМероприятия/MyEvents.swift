//
//  MyEvents.swift
//  VUCHOBE 1.0
//
//  Created by Rovshan on 10/12/2019.
//  Copyright © 2019 Rovshan. All rights reserved.
//

import UIKit

class MyEvents: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    var result = ["1","2","1","2","1","2","1","2"]
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        result.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "RecommendationCell") as? RecommendationCell else {
            return UITableViewCell()
        }
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    
}
