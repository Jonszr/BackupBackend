

class FileExplorer {

    pageStorage = []; // Stores contents of each page as navigation continues
    pageIndex = 0; // Root level
    animationDelayMs = 250;

    async GetCurrentProjectId() {
        let projectId = window.document.URL.split('/user/project/')[1].split('/')[0];
        return projectId;
    }

    async LoadNextFolder(folderId) {
        let res = await axios.get(`/api/v1/pri/user/project/folders/${folderId}`);
        return res;
    }

    async LoadNextFiles(folderId) {
        let res = await axios.get(`/api/v1/pri/user/project/files/${folderId}`);
        return res;
    }

    async SaveCurrentStructure() {

        let fileManagerContainer = document.querySelector('#file-manager');
        let fileContainerElems = fileManagerContainer.querySelectorAll('.view-container');

        if (this.pageStorage.length <= this.pageIndex) {
            this.pageStorage.push(fileContainerElems);
        }else {
            this.pageStorage[this.pageIndex] = fileContainerElems;
        }

    }

    async ClearPage(direction = "forward") {

        // Put away old elements
        let fileManagerContainer = document.querySelector('#file-manager');
        let fileContainerElems = fileManagerContainer.querySelectorAll('.view-container');

        for(let fileContainerElem of fileContainerElems) {

            // First remove the old animation classes to prevent confusion
            fileContainerElem.classList.remove('animate__animated');

            let animationRemoveDirection = direction == "forward" ? "fadeInLeft" : "fadeInRight";
            let animationAddDirection = direction == "forward" ? "fadeOutLeft" : "fadeOutRight";

            fileContainerElem.classList.remove(`animate__${animationRemoveDirection}`);

            console.log(animationRemoveDirection, animationAddDirection, fileContainerElem);

            // Then add the new animation classes
            fileContainerElem.classList.add('animate__animated');
            fileContainerElem.classList.add(`animate__${animationAddDirection}`);

            // Add a little delay between categories for aesthetic purposes
            await new Promise(r => setTimeout(r, this.animationDelayMs));
        }

        // Finally pop the element off as a dom child from the parent container.
        for(let fileContainerElem of fileContainerElems) {
            fileManagerContainer.removeChild(fileContainerElem);
        }

    }

    async OpenFolder(context) {

        console.log("Got context: " + context.id);
        let folderId = context.id.split('folder-')[1];

        await this.SaveCurrentStructure();
        let folderContentsFolders = this.LoadNextFolder(folderId);
        let filesContentsFolders = this.LoadNextFiles(folderId);

        console.log("Trying to block", `#${context.id}`);
        $(`#${context.id}`).block({
            message: '<div class="spinner-grow spinner-grow-sm text-white" role="status"></div>',
            timeout: 5000,
            css: {
              backgroundColor: 'transparent',
              border: '0'
            },
            overlayCSS: {
              opacity: 0.5
            }
        });

        folderContentsFolders = await folderContentsFolders;
        filesContentsFolders = await filesContentsFolders;

        $(`#${context.id}`).unblock();

        console.log(folderContentsFolders);
        let items = JSON.parse(folderContentsFolders.data.msg);
        let files = JSON.parse(filesContentsFolders.data.msg);

        await this.ClearPage("forward");

        // Then load the next folders
        let sampleCategoryHTML = document.querySelector('#js-component-sample-category').innerHTML;

        // 1. Create folder category view-container (this IS hacky)
        let nFolderCategory = document.createElement('div');
        nFolderCategory.classList.add('view-container');
        nFolderCategory.classList.add('animate__animated');
        nFolderCategory.classList.add('animate__fadeInRight');
        nFolderCategory.innerHTML = sampleCategoryHTML;
        nFolderCategory.querySelector('h6').textContent = "Folders";

        // Check to see if no folders are present
        if (!items.length) {
            let noContentElem = nFolderCategory.querySelector('#noCategoriesNotificationTemplate');
            noContentElem.id = 'no-content-folders';
            noContentElem.style.display = '';
        }

        // 2. Create files category view-container (this IS hacky)
        let nFileCategory = document.createElement('div');
        nFileCategory.classList.add('view-container');
        nFileCategory.classList.add('animate__animated');
        nFileCategory.classList.add('animate__fadeInRight');
        nFileCategory.innerHTML = sampleCategoryHTML;
        nFileCategory.querySelector('h6').textContent = "Files";

        // Check to see if no folders are present
        if (!files.length) {
            let noContentElem = nFileCategory.querySelector('#noCategoriesNotificationTemplate');
            noContentElem.id = 'no-content-files';
            noContentElem.style.display = '';
        }

        // 1. Create child-cards for folder category (this IS in-efficient)
        for (let folder of items) {

            let folderElem = document.createElement('div');
            folderElem.classList.add('card');
            folderElem.classList.add('file-manager-item');
            folderElem.classList.add('folder');
            folderElem.id = `folder-${folder.id}`;
            folderElem.onclick = _ => {
                window.FileExplorerInst.OpenFolder(folderElem);
            }

            let sampleFolderHTML = document.querySelector('#js-component-sample-folder').innerHTML;
            folderElem.innerHTML = sampleFolderHTML;
            folderElem.querySelector('#folderNameTemplate').textContent = folder.name;

            // Append child to this category
            nFolderCategory.appendChild(folderElem);
        }

        // 2. Create child-cards for files category (this IS in-efficient)
        for (let file of files) {

            let fileElem = document.createElement('div');
            fileElem.classList.add('card');
            fileElem.classList.add('file-manager-item');
            fileElem.classList.add('file');
            fileElem.id = `file-${file.assetId}`;

            let sampleFileHTML = document.querySelector('#js-component-sample-file').innerHTML;
            fileElem.innerHTML = sampleFileHTML;
            console.log("Using", file, file.assetName);
            fileElem.querySelector('#fileNameTemplate').textContent = file.assetName;

            // Append child to this category
            nFileCategory.appendChild(fileElem);

        }

        let fileManagerContainer = document.querySelector('#file-manager');

        // Add to DOM
        fileManagerContainer.appendChild(nFolderCategory);
        setTimeout(_ => {
            fileManagerContainer.appendChild(nFileCategory);
        }, this.animationDelayMs);

        // Make go back button visible
        let goBackBtn = document.querySelector('#goBackBtn');
        goBackBtn.classList.add('animate__animated');
        goBackBtn.classList.add('animate__bounceIn');
        goBackBtn.style.display = '';

        this.pageIndex++;

        return 0;
    }

    async GoBack(context) {

        // Remove everything from current page
        await this.ClearPage("backwards");
        this.pageIndex--;

        // Check if we are at the root page (then hide the back button button)
        if (this.pageIndex == 0) {
            // Make go back button visible
            let goBackBtn = document.querySelector('#goBackBtn');
            goBackBtn.classList.remove('animate__animated');
            goBackBtn.classList.remove('animate__bounceIn');

            // Some animation glitch with this button
            // goBackBtn.classList.add('animate__animated');
            // goBackBtn.classList.add('animate__bounceOut')

            setTimeout(_ => { goBackBtn.style.display = 'none'; }, 1);
        }

        console.log("Current page index", this.pageIndex, this.pageStorage[this.pageIndex]);
        let fileManagerContainer = document.querySelector('#file-manager');

        for (let item of this.pageStorage[this.pageIndex]) {
            item.classList.remove('animate__animated');
            item.classList.remove('animate__fadeOutLeft');
            item.classList.remove('animate__fadeInRight')

            item.classList.add('animate__animated');
            item.classList.add('animate__fadeInLeft');

            fileManagerContainer.appendChild(item);
            await new Promise(r=>setTimeout(r, this.animationDelayMs));
        }


        return 0;
    }

    static async HookFolders() {
        console.log("Hooking folders");
    }


}

let fileExplorerInst = new FileExplorer();

console.log(fileExplorerInst.OpenFolder);

window.FileExplorerInst = fileExplorerInst;